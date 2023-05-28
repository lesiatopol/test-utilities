/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * User: Lesia Topol
 * Date: 5/20/2022
 * All rights reserved
 */

package com.lt.testframework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertyReader {
    private static final Logger logger = LoggerFactory.getLogger(PropertyReader.class);

    public static String readProperty(String path, String fileName, String property) {
        final File file = new File(path + fileName + ".properties");
        try (FileInputStream inStream = new FileInputStream(file)) {
            Properties properties = new Properties();
            properties.load(inStream);
            logger.info("Property was assigned value - {} = {}", property, properties.getProperty(property));
            return properties.getProperty(property);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("Property {} was not found", property);
        return "NA";
    }
}
