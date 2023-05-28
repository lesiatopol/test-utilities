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

package com.lt.testframework.stepimpl;

import com.lt.testframework.awsmanager.S3Manager;
import com.lt.testframework.config.AppContextConfig;
import com.lt.testframework.utils.FileReader;
import net.thucydides.core.annotations.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@ContextConfiguration(classes = {AppContextConfig.class})
public class S3StepImpl {

    protected static final Logger logger = LoggerFactory.getLogger(S3StepImpl.class);
    @Autowired
    private S3Manager s3Manager;
    @Autowired
    private FileReader fileReader;

    @Step
    public void putFileIntoBucket(String inputFile, String bucket, String folder, String region) {
        try {
            s3Manager.setAwsRegion(region);
            s3Manager.buildClient();
            s3Manager.putFileIntoS3Bucket(bucket, folder, fileReader.readFile(inputFile));
            logger.info("Put File is success with Content MD5 = {}", s3Manager.getPutObjectResult().getContentMd5());
            assertNotNull(s3Manager.getPutObjectResult(), "Put File is not success, result is null");
        } catch (NullPointerException e) {
            logger.info("Put File has failed with error {}", Arrays.toString(e.getStackTrace()));
            fail("Put File has failed with error\n" + e.getMessage());
        }
    }

    @Step
    public void getFileFromBucket(String expectedFileName, String bucket, String folder, String region) {
        try {
            s3Manager.setAwsRegion(region);
            s3Manager.buildClient();
            String actualFile = s3Manager.getObjectFromS3Bucket(bucket, folder, expectedFileName);
            assertEquals(expectedFileName, actualFile, "Expected file is not found");
        } catch (NullPointerException e) {
            logger.info("File look up has failed with error {}", Arrays.toString(e.getStackTrace()));
            fail("File look up has failed with error\n" + e.getMessage());
        }
    }
}
