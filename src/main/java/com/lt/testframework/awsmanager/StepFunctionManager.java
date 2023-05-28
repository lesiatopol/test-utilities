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

package com.lt.testframework.awsmanager;

import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StepFunctionManager {

    protected static final Logger logger = LoggerFactory.getLogger(StepFunctionManager.class);
    @Value("${aws.default.region}")
    private String awsRegion;
    @Autowired
    private CredentialsProvider credentialsProvider;

    private AWSStepFunctions stepFunctionsClient;

    private StartExecutionResult stepFunctionExecutionResult;

    public StepFunctionManager() {
    }

    public void buildClient() {
        logger.info("Step Function client is in {} region", awsRegion);
        this.stepFunctionsClient = AWSStepFunctionsClientBuilder.standard()
                .withCredentials(credentialsProvider.getAwsCredentialsProvider())
                .withRegion(awsRegion)
                .build();
    }

    public StartExecutionResult executeStepFunctionRequest(String stepFunctionName, String inputJsonFile) {
        StartExecutionRequest startExecutionRequest = new StartExecutionRequest()
                .withStateMachineArn(getStateMachineArn(stepFunctionName))
                .withInput(inputJsonFile);
        stepFunctionExecutionResult = stepFunctionsClient.startExecution(startExecutionRequest);
        return stepFunctionExecutionResult;
    }

    private String getStateMachineArn(String stepFunctionName) {
        ListStateMachinesResult stateMachinesList = stepFunctionsClient.
                listStateMachines(new ListStateMachinesRequest());
        List<StateMachineListItem> stateMachines = stateMachinesList
                .getStateMachines();
        return stateMachines.stream()
                .filter(sm -> sm.getName().equals(stepFunctionName))
                .map(StateMachineListItem::getStateMachineArn)
                .findAny()
                .orElse("");
    }

    public void setAwsRegion(String awsRegion) {
        this.awsRegion = awsRegion;
    }

    public StartExecutionResult getStepFunctionExecutionResult() {
        return stepFunctionExecutionResult;
    }
}
