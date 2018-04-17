package io.syndesis.connector.rest.swagger.springboot;

import javax.annotation.Generated;
import io.syndesis.connector.rest.swagger.AuthenticationType;

/**
 * REST Swagger Connector
 *
 * Generated by camel-package-maven-plugin - do not edit this file!
 */
@Generated("org.apache.camel.maven.connector.SpringBootAutoConfigurationMojo")
public class SwaggerConnectorConnectorConfigurationCommon {

    /**
     * API basePath for example /v2. Default is unset if set overrides the value
     * present in Swagger specification.
     */
    private String basePath;
    /**
     * Scheme hostname and port to direct the HTTP requests to in the form of
     * https://hostname:port. Can be configured at the endpoint component or in
     * the correspoding REST configuration in the Camel Context. If you give
     * this component a name (e.g. petstore) that REST configuration is
     * consulted first rest-swagger next and global configuration last. If set
     * overrides any value found in the Swagger specification RestConfiguration.
     * Can be overriden in endpoint configuration.
     */
    private String host;
    /**
     * ID of the operation from the Swagger specification.
     */
    private String operationId;
    private String specification;
    private AuthenticationType authenticationType;
    private String accessToken;
    private String refreshToken;
    private String username;
    private String password;
    private String clientId;
    private String clientSecret;
    private String tokenEndpoint;
    private Boolean authorizeUsingParameters;
    private String refreshTokenRetryStatuses;

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public AuthenticationType getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(AuthenticationType authenticationType) {
        this.authenticationType = authenticationType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getTokenEndpoint() {
        return tokenEndpoint;
    }

    public void setTokenEndpoint(String tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
    }

    public Boolean getAuthorizeUsingParameters() {
        return authorizeUsingParameters;
    }

    public void setAuthorizeUsingParameters(Boolean authorizeUsingParameters) {
        this.authorizeUsingParameters = authorizeUsingParameters;
    }

    public String getRefreshTokenRetryStatuses() {
        return refreshTokenRetryStatuses;
    }

    public void setRefreshTokenRetryStatuses(String refreshTokenRetryStatuses) {
        this.refreshTokenRetryStatuses = refreshTokenRetryStatuses;
    }
}
