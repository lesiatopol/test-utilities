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

import com.lt.testframework.awsmanager.StepFunctionManager;
import com.lt.testframework.config.AppContextConfig;
import com.lt.testframework.datamanager.DataDecorator;
import com.lt.testframework.datamanager.DataStorage;
import net.thucydides.core.annotations.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import io.cucumber.datatable.DataTable;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;

@ContextConfiguration(classes = {AppContextConfig.class})
public class StepFunctionStepImpl {

    protected static final Logger logger = LoggerFactory.getLogger(StepFunctionStepImpl.class);
    @Autowired
    private StepFunctionManager stepFunctionManager;
    @Autowired
    private DataDecorator dataDecorator;

    @Step
    public void executeStepFunction(String stepFunctionName, String region, String inputJsonFile, DataTable dataSet) {
        try {
            stepFunctionManager.setAwsRegion(region);
            stepFunctionManager.buildClient();
            stepFunctionManager.executeStepFunctionRequest(stepFunctionName, dataDecorator.decorateDataFromTest(inputJsonFile, dataSet));
            logger.info("Execute Step Function is success with execution arn = {}", stepFunctionManager.getStepFunctionExecutionResult().getExecutionArn());
            DataStorage.getInstance().setNewData("BODY_TO_VALIDATE", stepFunctionManager.getStepFunctionExecutionResult().getExecutionArn());
            assertNotNull(stepFunctionManager.getStepFunctionExecutionResult(), "Execute Step Function is not success, result is null");
        } catch (NullPointerException e) {
            logger.info("Execute Step Function has failed with error {}", Arrays.toString(e.getStackTrace()));
            fail("Execute Step Function has failed with error\n" + e.getMessage());
        }
    }
}
