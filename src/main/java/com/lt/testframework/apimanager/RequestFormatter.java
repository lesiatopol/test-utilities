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

import com.lt.testframework.datamanager.DataStorage;
import com.lt.testframework.utils.EnvironmentContext;
import com.lt.testframework.utils.JsonUtils;
import com.lt.testframework.utils.PropertyReader;
import com.lt.testframework.utils.YamlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class RequestFormatter {
    private static final Logger logger = LoggerFactory.getLogger(RequestFormatter.class);
    @Autowired
    private EnvironmentContext environmentContext;
    @Autowired
    private RequestMetadata requestMetadata;
    public RequestFormatter() {
    }

    public RequestMetadata mapRequestDataDefaults(String fileName) {
        logger.info("Ream yaml file content and map to request metadata");
        environmentContext.setEnvironment();
        try {
            requestMetadata.populateRequestData(YamlReader.readYaml(fileName));
            final String host = requestMetadata.getHost();
            final String url = PropertyReader.readProperty(environmentContext.getEnvironment(), host, "hostname");
            requestMetadata.setBaseURL(url);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return requestMetadata;
    }

    public RequestMetadata customizeRequestData(String requestElement, Map<String, String> requestUpdates) {
        switch (requestElement) {
            case "body":
                requestMetadata.setPayload(JsonUtils.setJsonValue(requestMetadata.getPayload(), requestUpdates));
                break;
            case "endpoint":
                requestUpdates.forEach((k, v) ->
                        requestMetadata.setEndpoint(requestMetadata.getEndpoint()
                                .replace("{" + k + "}", ((v.startsWith("%") && v.endsWith("%"))
                                        ? DataStorage.getInstance().getExistingData(v) : v)))
                );
                logger.info("Request endpoint was update with - {}", requestMetadata.getEndpoint());
                break;
            case "headers":
                requestUpdates.forEach((k, v) -> {
                    requestMetadata.setHeaders(k, v);
                    logger.info("Request was update with headers - {} = {}", k, v);
                });
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestElement);
        }
        return requestMetadata;
    }
}
