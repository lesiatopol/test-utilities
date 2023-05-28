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

package com.lt.testframework.utils;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Component
public class FileReader {
    protected static final Logger logger = LoggerFactory.getLogger(FileReader.class);
    @Value("${path.to.test.data.folder}")
    private String pathToTestDataFolder;

    public FileReader() {
    }

    public String readFileAsString(String inputFileName) {
        String fileAsString = null;
        try {
            if (!inputFileName.equals("N/A")) {
                String filePath = System.getProperty("user.dir") + File.separator + pathToTestDataFolder + inputFileName;
                fileAsString = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
                logger.info("File path {} read operation is success", filePath);
            } else {
                logger.info("File payload not applicable for API under the test");
            }
        } catch (Exception e) {
            logger.info("Error while reading File - \n {}", e.toString());
        }
        return fileAsString == null ? "" : fileAsString;
    }

    public File readFile(String inputFileName) {
        File file = null;
        try {
            String filePath = System.getProperty("user.dir") + File.separator + pathToTestDataFolder + inputFileName;
            file = new File(filePath);
            logger.info("File path {} read operation is success", filePath);
        } catch (Exception e) {
            logger.info("Error while reading File - \n {}", e.toString());
        }
        return file;
    }

}
