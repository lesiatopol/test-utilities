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

import com.lt.testframework.awsmanager.SNSManager;
import com.lt.testframework.config.AppContextConfig;
import com.lt.testframework.datamanager.DataDecorator;
import io.cucumber.datatable.DataTable;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import net.thucydides.core.annotations.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;

@ContextConfiguration(classes = {AppContextConfig.class})
public class SNSStepImpl {
    protected static final Logger logger = LoggerFactory.getLogger(SNSStepImpl.class);
    @Autowired
    private SNSManager snsManager;
    @Autowired
    private DataDecorator dataDecorator;

    @Step
    public void publishEvent(String inputJsonFile, String topic, String region, DataTable dataSet) {
        try {
            snsManager.setAwsRegion(region);
            snsManager.buildClient();
            snsManager.publishEvent(topic, dataDecorator.decorateDataFromTest(inputJsonFile, dataSet));
            logger.info("Publish Event is success with Sequence Number = {}", snsManager.getPublishResult().getSequenceNumber());
            assertNotNull(snsManager.getPublishResult(), "Publish Event is not success, result is null");
        } catch (NullPointerException e) {
            logger.info("Publish Event has failed with error {}", Arrays.toString(e.getStackTrace()));
            fail("Publish Event has failed with error\n" + e.getMessage());
        }
    }
}
