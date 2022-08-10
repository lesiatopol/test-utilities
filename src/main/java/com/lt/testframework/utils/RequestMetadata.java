/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * User: Lesia Topol
 * Date: 7/10/2022
 * All rights reserved
 */

package com.lt.testframework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class RequestMetadata {
    private String host;
    private String method;
    private String endpoint;
    private String payload;
    private String contentType;
    private String accept;
    private String authorization;
    private String baseURL;
    private Map<String, String> headers;
    protected static final Logger logger = LoggerFactory.getLogger(RequestMetadata.class);

    public RequestMetadata(Map<String, Object> data) {
        data.forEach((key, value) -> {
            if (key.equals("attributes")) {
                Map<String, String> attributes = (Map<String, String>) data.get(key);
                this.method = attributes.get("method");
                this.host = attributes.get("host");
                this.payload = JsonUtils.readJsonAsString((String) attributes.get("payload"));
                this.endpoint = attributes.get("endpoint");
            }
            if (key.equals("headers")) {
                Map<String, String> headersList = (Map) data.get(key);
                this.headers = headersList;
                this.contentType = headersList.get("Content-Type");
                this.accept = headersList.get("Accept");
                this.authorization = headersList.get("Authorization");
            }
            logger.info("Request defaults: \n{} = {} \n", key, value);
        });
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
}
