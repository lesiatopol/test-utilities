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

import com.lt.testframework.awsmanager.DynamoDBManager;
import com.lt.testframework.awsmanager.PostgresSQLManager;
import com.lt.testframework.config.AppContextConfig;
import io.cucumber.datatable.DataTable;

import net.thucydides.core.annotations.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {AppContextConfig.class})
public class DBStepImpl {
    protected static final Logger logger = LoggerFactory.getLogger(DBStepImpl.class);
    @Autowired
    private DynamoDBManager dynamoDBManager;

    @Autowired
    private PostgresSQLManager postgresSQLManager;

    @Step
    public void readDynamoDBRecord(String tableName, String region, DataTable dataSet) {
        try {
            dynamoDBManager.setAwsRegion(region);
            dynamoDBManager.buildClient();
            dynamoDBManager.getDynamoDbItem(tableName, dataSet.asMap(String.class, String.class));
            logger.info("Query DynamoDB is success with # records returned = {}", dynamoDBManager.getGetItemResult().getItem().size());
            assertFalse(dynamoDBManager.getGetItemResult().getItem().isEmpty(), "Query DynamoDB is not success, result is empty");
        } catch (NullPointerException e) {
            logger.info("Query DynamoDB has failed with error {}", Arrays.toString(e.getStackTrace()));
            fail("Query DynamoDB has failed with error\n" + e.getMessage());
        }
    }

    @Step
    public void readPostgresSQLRecord(String query, String region, String expectedNumberOfRecords) {
        postgresSQLManager.setAwsRegion(region);
        int actualNumberOfRecords = postgresSQLManager.getPostgresSqlItemAsMap(query).size();
        assertEquals(actualNumberOfRecords, Integer.parseInt(expectedNumberOfRecords), "Query PostgresSQL return  records, expected - " + expectedNumberOfRecords + ", actual - " + actualNumberOfRecords);
    }
}
