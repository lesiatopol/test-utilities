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

package com.lt.testframework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DataStorage {
    private static Map<String, String> dataStorage = new HashMap<>();
    protected static final Logger logger = LoggerFactory.getLogger(DataStorage.class);

    private DataStorage() {
    }

    public static String setNewData(String key, String value) {
        logger.info("DataStorage: key - {}, pair - {}", key, value);
        return (value == null || value.isEmpty()) ? null : dataStorage.put(key, value);
    }

    public static String getExistingData(String key) {
        return dataStorage.get(key);
    }

    public static String prepareData(String inputToUpdate, String... keys) {
        if (inputToUpdate == null) {
            return null;
        }
        String outputResult = inputToUpdate;
        for (String key : keys) {
            if (dataStorage.containsKey(key)) {
                outputResult = outputResult.replace("%" + key + "%", dataStorage.get(key));
            }
        }
        logger.info("{} was update to - {}", inputToUpdate, outputResult);
        return outputResult;
    }

    public static void cleanUpDataStorage() {
        dataStorage.clear();
        logger.info("DataStorage is empty now");
    }
}
