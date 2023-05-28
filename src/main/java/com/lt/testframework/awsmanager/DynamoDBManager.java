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

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.amazonaws.services.dynamodbv2.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DynamoDBManager {
    protected static final Logger logger = LoggerFactory.getLogger(DynamoDBManager.class);
    @Value("${aws.default.region}")
    private String awsRegion;

    @Autowired
    private CredentialsProvider credentialsProvider;
    private AmazonDynamoDB amazonDynamoDBClient;
    private GetItemResult getItemResult;

    public DynamoDBManager() {
    }

    public void buildClient() {
        logger.info("DynamoDB client is in {} region", awsRegion);
        this.amazonDynamoDBClient = AmazonDynamoDBClientBuilder
                .standard()
                .withRegion(awsRegion)
                .withCredentials(credentialsProvider.getAwsCredentialsProvider())
                .build();
    }

    public GetItemResult getDynamoDbItem(String table, Map<String, String> attributes) {
        HashMap<String, AttributeValue> attributesToGet = new HashMap<>();
        attributes.forEach((name, queryValue) -> {
            attributesToGet.put(name, new AttributeValue(queryValue));
        });
        try {
            GetItemRequest getItemRequest = new GetItemRequest()
                    .withTableName(table)
                    .withReturnConsumedCapacity(ReturnConsumedCapacity.TOTAL)
                    .withKey(attributesToGet);
            getItemResult = amazonDynamoDBClient.getItem(getItemRequest);
        } catch (DynamoDBMappingException e) {
            logger.warn(e.getMessage());
            System.exit(1);
        }
        return getItemResult;
    }

    public Map<String, Object> formatGetItemResult(GetItemResult getItemResult) {
        Map<String, Object> formattedKeyValueMap = new HashMap<>();
        getItemResult.getItem().forEach((name, value) -> {
            if (value.getS() instanceof String) {
                formattedKeyValueMap.put(name, value.getS());
            } else {
                formattedKeyValueMap.put(name, value.getN());
            }
        });
        return formattedKeyValueMap;
    }

    public GetItemResult getGetItemResult() {
        return getItemResult;
    }

    public void setAwsRegion(String awsRegion) {
        this.awsRegion = awsRegion;
    }

}
