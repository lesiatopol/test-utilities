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

package com.lt.testframework.stepdefinitions;

import io.cucumber.datatable.DataTable;
import com.lt.testframework.stepimpl.DBStepImpl;
import io.cucumber.java.en.Given;
import net.thucydides.core.annotations.Steps;

public class DBStepDef {

    @Steps
    private DBStepImpl dbSteps;

    @Given("^validate DynamoDB (.*) table in (.*) region has data$")
    public void queryDynamoDB(String tableName, String region, DataTable dataSet) throws Throwable {
        dbSteps.readDynamoDBRecord(tableName, region, dataSet);
    }

    @Given("^validate PostgresSql (.*) query return (.*) records in (.*) region$")
    public void queryPostgresSql(String query, String expectedNumberOfRecords, String region) throws Throwable {
        dbSteps.readPostgresSQLRecord(query, region, expectedNumberOfRecords);
    }
}