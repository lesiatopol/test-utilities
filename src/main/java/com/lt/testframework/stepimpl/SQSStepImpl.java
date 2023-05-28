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

import com.lt.testframework.awsmanager.SQSManager;
import com.lt.testframework.config.AppContextConfig;
import com.lt.testframework.datamanager.DataDecorator;
import com.lt.testframework.datamanager.DataStorage;
import io.cucumber.datatable.DataTable;
import net.thucydides.core.annotations.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@ContextConfiguration(classes = {AppContextConfig.class})
public class SQSStepImpl {

    protected static final Logger logger = LoggerFactory.getLogger(SQSStepImpl.class);
    @Autowired
    private SQSManager sqsManager;
    @Autowired
    private DataDecorator dataDecorator;

    @Step
    public void sendSQSMessage(String inputJsonFile, String queue, String region, DataTable dataSet) {
        try {
            sqsManager.setAwsRegion(region);
            sqsManager.buildClient();
            sqsManager.sendMessage(queue, dataDecorator.decorateDataFromTest(inputJsonFile, dataSet));
            logger.info("Send Message is success with Sequence Number = {}", sqsManager.getSendMessageResult().getSequenceNumber());
            assertNotNull(sqsManager.getSendMessageResult(), "Publish Event is not success, result is null");
        } catch (NullPointerException e) {
            logger.info("Send Message has failed with error {}", Arrays.toString(e.getStackTrace()));
            fail("Send Message has failed with error\n" + e.getMessage());
        }
    }

    @Step
    public void receiveSQSMessage(String filter, String queue, String region) {
        try {
            sqsManager.setAwsRegion(region);
            sqsManager.buildClient();
            String receivedMsgBody = sqsManager.receiveMessage(queue, filter);
            DataStorage.getInstance().setNewData("BODY_TO_VALIDATE", receivedMsgBody);
            assertNotNull(receivedMsgBody, "Receive Message is not success, result is null");
        } catch (NullPointerException e) {
            logger.info("Receive Message has failed with error {}", Arrays.toString(e.getStackTrace()));
            fail("Receive Message has failed with error\n" + e.getMessage());
        }
    }
}
