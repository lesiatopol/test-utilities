/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * User: Lesia Topol
 * Date: 5/20/2023
 * All rights reserved
 */

package com.lt.testframework.datamanager;

import com.lt.testframework.utils.FileReader;
import com.lt.testframework.utils.JsonUtils;
import io.cucumber.datatable.DataTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class DataDecorator {
    protected static final Logger logger = LoggerFactory.getLogger(DataDecorator.class);
    @Autowired
    private FileReader fileReader;
    public DataDecorator(){}

    public String decorateDataFromDataStorage(String inputToUpdate, String... keys) {
        if (inputToUpdate == null) {
            return null;
        }
        String outputResult = inputToUpdate;
        for (String key : keys) {
            if (DataStorage.getInstance().getDataStorage().containsKey(key)) {
                outputResult = outputResult.replace("%" + key + "%", DataStorage.getInstance().getExistingData(key));
            }
        }
        logger.info("{} was update to - {}", inputToUpdate, outputResult);
        return outputResult;
    }

    public String decorateDataFromTest(String inputJsonFile, DataTable dataSet) {
        return JsonUtils.setJsonValue(fileReader.readFileAsString(inputJsonFile), dataSet.asMap(String.class, String.class));
    }
}
