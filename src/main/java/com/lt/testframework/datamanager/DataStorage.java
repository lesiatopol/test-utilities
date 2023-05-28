/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * User: Lesia Topol
 * Date: 5/26/2022
 * All rights reserved
 */

package com.lt.testframework.datamanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class DataStorage {
    protected static final Logger logger = LoggerFactory.getLogger(DataStorage.class);
    private static Map<String, String> dataStorage = new HashMap<>();
    private static final DataStorage instance = new DataStorage();

    public static DataStorage getInstance() {
        return instance;
    }

    private DataStorage() {
        setNewData("%randomNumber%", String.valueOf(ThreadLocalRandom.current().nextInt(0, 100000)));
    }

    public String setNewData(String key, String value) {
        logger.info("DataStorage new data: key - {}, value - {}", key, value);
        return (value == null || value.isEmpty()) ? null : dataStorage.put(key, value);
    }

    public String getExistingData(String key) {
        return dataStorage.get(key);
    }

    public Map<String, String> getDataStorage() {
        return dataStorage;
    }

    public void purgeDataStorage() {
        dataStorage.clear();
        logger.info("DataStorage has been purged");
    }
}
