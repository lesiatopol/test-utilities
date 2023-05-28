/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * User: Lesia Topol
 * Date: 5/26/2023
 * All rights reserved
 */

package com.lt.testframework.apimanager;

import com.lt.testframework.utils.FileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RequestMetadata {
    private String host;
    private String method;
    private String endpoint;
    private String payload;
    private String contentType;
    private String accept;
    private String authorization;
    private String baseURL;
    @Autowired
    private FileReader fileReader;
    private Map<String, String> headers;
    protected static final Logger logger = LoggerFactory.getLogger(RequestMetadata.class);
    public RequestMetadata() {
    }
    public void setHost(String host) {
        this.host = host;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public void setHeaders(String headerName, String headervalue) {
        this.headers.put(headerName, headervalue);

    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getHost() {
        return host;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getMethod() {
        return method;
    }

    public String getPayload() {
        return payload;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public String getContentType() {
        return contentType;
    }

    public String getAccept() {
        return accept;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void populateRequestData (Map<String, Object> requestAttributes) {
        requestAttributes.forEach((key, value) -> {
            if (key.equals("attributes")) {
                Map<String, String> attributes = (Map<String, String>) requestAttributes.get(key);
                this.method = attributes.get("method");
                this.host = attributes.get("host");
                this.payload = fileReader.readFileAsString((String) attributes.get("payload"));
                this.endpoint = attributes.get("endpoint");
            }
            if (key.equals("headers")) {
                Map<String, String> headersList = (Map) requestAttributes.get(key);
                this.headers = headersList;
                this.contentType = headersList.get("Content-Type");
                this.accept = headersList.get("Accept");
                this.authorization = headersList.get("Authorization");
            }
            logger.info("API Request defaults: \n{} = {} \n", key, value);
        });
    }
}
