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

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class LambdaManager {

    protected static final Logger logger = LoggerFactory.getLogger(LambdaManager.class);
    @Value("${aws.default.region}")
    private String awsRegion;
    @Autowired
    private CredentialsProvider credentialsProvider;

    private AWSLambda amazonLambdaClient;
    private InvokeResult lambdaInvokeResult;

    public LambdaManager() {
    }

    public void buildClient() {
        logger.info("Lambda client is in {} region", awsRegion);
        this.amazonLambdaClient = AWSLambdaClientBuilder.standard()
                .withCredentials(credentialsProvider.getAwsCredentialsProvider())
                .withRegion(awsRegion)
                .build();
    }

    public void invokeLambda(String lambda, String requestBody) {
        InvokeRequest invokeRequest = new InvokeRequest()
                .withFunctionName(lambda)
                .withPayload(requestBody);
        lambdaInvokeResult = amazonLambdaClient.invoke(invokeRequest);
    }

    public String getLambdaInvokeResultAsString() {
        return new String(lambdaInvokeResult.getPayload().array(), StandardCharsets.UTF_8);
    }

    public void setAwsRegion(String awsRegion) {
        this.awsRegion = awsRegion;
    }

    public InvokeResult getLambdaInvokeResult() {
        return lambdaInvokeResult;
    }
}
