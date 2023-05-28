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

import com.lt.testframework.awsmanager.LambdaManager;
import com.lt.testframework.datamanager.DataDecorator;
import com.lt.testframework.datamanager.DataStorage;
import io.cucumber.datatable.DataTable;
import net.thucydides.core.annotations.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class LambdaStepImpl {

    protected static final Logger logger = LoggerFactory.getLogger(LambdaStepImpl.class);
    @Autowired
    private LambdaManager lambdaManager;
    @Autowired
    private DataDecorator dataDecorator;

    @Step
    public void readLambdaResponse(String lambda, String inputJsonFile, String region, DataTable dataSet) {
        try {
            lambdaManager.setAwsRegion(region);
            lambdaManager.buildClient();
            lambdaManager.invokeLambda(lambda, dataDecorator.decorateDataFromTest(inputJsonFile, dataSet));
            logger.info("Invoke Lambda is success with the result = {}", lambdaManager.getLambdaInvokeResultAsString());
            DataStorage.getInstance().setNewData("BODY_TO_VALIDATE", lambdaManager.getLambdaInvokeResultAsString());
            assertNotNull(lambdaManager.getLambdaInvokeResultAsString(), "Invoke Lambda is not success, result is null");
        } catch (NullPointerException e) {
            logger.info("Invoke Lambda has failed with error {}", Arrays.toString(e.getStackTrace()));
            fail("Invoke Lambda has failed with error\n" + e.getMessage());
        }
    }

    @Step
    public void checkLambdaStatusCode(String expectedStatusCode) {
        logger.info("Invoke Lambda is success with the result = {}", lambdaManager.getLambdaInvokeResultAsString());
        assertEquals(lambdaManager.getLambdaInvokeResult().getStatusCode().toString(), expectedStatusCode, "Lambda Status Code is not as expected");
    }
}
