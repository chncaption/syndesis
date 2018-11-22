/*
 * Copyright (C) 2016 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.google.sheets.stream;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.ScheduledBatchPollingConsumer;
import org.apache.camel.util.CastUtils;
import org.apache.camel.util.ObjectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * The GoogleSheets consumer.
 */
public class GoogleSheetsStreamConsumer extends ScheduledBatchPollingConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(GoogleSheetsStreamConsumer.class);

    public GoogleSheetsStreamConsumer(Endpoint endpoint, Processor processor) {
        super(endpoint, processor);
    }

    protected GoogleSheetsStreamConfiguration getConfiguration() {
        return getEndpoint().getConfiguration();
    }

    protected Sheets getClient() {
        return getEndpoint().getClient();
    }

    @Override
    public GoogleSheetsStreamEndpoint getEndpoint() {
        return (GoogleSheetsStreamEndpoint)super.getEndpoint();
    }

    @Override
    protected int poll() throws Exception {
        Queue<Exchange> answer = new ArrayDeque<>();

        if (ObjectHelper.isNotEmpty(getConfiguration().getRange())) {
            Sheets.Spreadsheets.Values.BatchGet request = getClient().spreadsheets().values().batchGet(getConfiguration().getSpreadsheetId());

            request.setMajorDimension(getConfiguration().getMajorDimension());
            request.setValueRenderOption(getConfiguration().getValueRenderOption());

            if (getConfiguration().getRange().contains(",")) {
                request.setRanges(Arrays.stream(getConfiguration().getRange().split(","))
                        .map(String::trim)
                        .collect(Collectors.toList()));
            } else {
                request.setRanges(Collections.singletonList(getConfiguration().getRange()));
            }

            BatchGetValuesResponse response = request.execute();

            if (response.getValueRanges() != null) {
                response.getValueRanges()
                        .stream()
                        .limit(getConfiguration().getMaxResults())
                        .map(valueRange -> getEndpoint().createExchange(valueRange))
                        .forEach(answer::add);
            }
        } else {
            Sheets.Spreadsheets.Get request = getClient().spreadsheets().get(getConfiguration().getSpreadsheetId());

            request.setIncludeGridData(getConfiguration().isIncludeGridData());

            Spreadsheet spreadsheet = request.execute();
            answer.add(getEndpoint().createExchange(spreadsheet));
        }

        return processBatch(CastUtils.cast(answer));
    }

    @Override
    public int processBatch(Queue<Object> exchanges) throws Exception {
        int total = exchanges.size();

        for (int index = 0; index < total && isBatchAllowed(); index++) {
            // only loop if we are started (allowed to run)
            final Exchange exchange = ObjectHelper.cast(Exchange.class, exchanges.poll());
            // add current index and total as properties
            exchange.setProperty(Exchange.BATCH_INDEX, index);
            exchange.setProperty(Exchange.BATCH_SIZE, total);
            exchange.setProperty(Exchange.BATCH_COMPLETE, index == total - 1);

            // update pending number of exchanges
            pendingExchanges = total - index - 1;

            getAsyncProcessor().process(exchange, doneSync -> LOG.trace("Processing exchange done"));
        }

        return total;
    }
}
