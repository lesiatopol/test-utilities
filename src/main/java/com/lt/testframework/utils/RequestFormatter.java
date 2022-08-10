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

import com.lt.testframework.DataStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import static com.lt.testframework.utils.EnvironmentContext.getEnvironment;

public class RequestFormatter {
    private static final Logger logger = LoggerFactory.getLogger(RequestFormatter.class);
    private RequestMetadata requestMetadata;

    public RequestMetadata readDefaults(String fileName) {
        logger.info("Ream yaml file content and map to request metadata");
        try {
            requestMetadata = YamlReader.readYaml(fileName);
            final String host = requestMetadata.getHost();
            final String url = readPropertyValue(getEnvironment(), host, "hostname");
            requestMetadata.setBaseURL(url);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return requestMetadata;
    }

    public RequestMetadata updateREST(String requestElement, Map<String, String> requestUpdates) {
        switch (requestElement) {
            case "body":
                requestMetadata.setPayload(JsonUtils.setJsonValue(requestMetadata.getPayload(), requestUpdates));
                break;
            case "endpoint":
                requestUpdates.forEach((k, v) ->
                        requestMetadata.setEndpoint(requestMetadata.getEndpoint()
                                .replace("{" + k + "}", ((v.startsWith("%") && v.endsWith("%"))
                                                ? DataStorage.getExistingData(v) : v)))
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

    private String readPropertyValue(String path, String fileName, String property) {
        final File file = new File(path + fileName + ".properties");
        try (FileInputStream inStream = new FileInputStream(file)) {
            Properties properties = new Properties();
            properties.load(inStream);
            logger.info("Property {} was assigned value - {}", property, properties.getProperty(property));
            return properties.getProperty(property);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("Property {} was not found", property);
        return "NA";
    }
}
