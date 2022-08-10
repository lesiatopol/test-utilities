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

import com.lt.testframework.utils.*;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class BaseTest {
    private static final BaseTest instance = new BaseTest();

    private BaseTest() {
    }

    public static BaseTest getInstance() {
        EnvironmentContext.setEnvironment();
        DataStorage.setNewData("%randomNumber%", String.valueOf(ThreadLocalRandom.current().nextInt(0, 100000)));
        return instance;
    }

    public static String preparePayload(String jsonFileName, Map<String, String> keyValueToUpdate) {
        String defaultPayload= JsonUtils.readJsonAsString(jsonFileName);
        return JsonUtils.setJsonValue(defaultPayload, keyValueToUpdate);
    }
}
