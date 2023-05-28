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

package com.lt.testframework.utils;

import com.jayway.jsonpath.JsonPath;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

public class JsonUtils {
    protected static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static final JSONParser jsonParser = new JSONParser();

    private JsonUtils() {
    }

    public static Object getJsonValue(String jsonAsString, String key) {
        if (jsonAsString == null || jsonAsString.isEmpty()) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray;
        try {
            Object parsedJson = jsonParser.parse(jsonAsString);
            if (parsedJson instanceof JSONObject) {
                jsonObject = (JSONObject) parsedJson;
                Optional<Object> actualValue = Optional.ofNullable(JsonPath.read(jsonObject, key));
                return (actualValue.isPresent() ? JsonPath.read(jsonObject, key).toString() : "null");
            }
            if (parsedJson instanceof JSONArray) {
                jsonArray = (JSONArray) parsedJson;
                for (Object o : jsonArray) {
                    jsonObject = (JSONObject) o;
                }
                return (jsonObject.get(key) != null ? jsonObject.get(key) : jsonObject.get(key).toString());
            }
            return jsonObject.toString();
        } catch (
                ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String setJsonValue(String jsonAsString, Map<String, String> attributesToUpdate) {
        if (jsonAsString == null || jsonAsString.isEmpty()) {
            return null;
        }
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonAsString);
            for (Map.Entry<String, String> entry : attributesToUpdate.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (JsonPath.read(jsonObject, key) != null) {
                    jsonObject = JsonPath.parse(jsonObject).set(key, value).json();
                    logger.info("Request body was update with - {} = {}", key, value);
                }
            }
            return jsonObject.toJSONString();
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
