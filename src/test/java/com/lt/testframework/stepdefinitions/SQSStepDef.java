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

import com.lt.testframework.stepimpl.SQSStepImpl;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import net.thucydides.core.annotations.Steps;

public class SQSStepDef {
    @Steps
    private SQSStepImpl sqsSteps;

    @Given("^system sends SQS message (.*) to (.*) in (.*) region with data$")
    public void sendMessageToSQS(String inputJsonFile, String queue, String region, DataTable dataTable) throws Throwable {
        sqsSteps.sendSQSMessage(inputJsonFile, queue, region, dataTable);
    }

    @Given("^system receives SQS message with (.*) at (.*) in (.*) region$")
    public void receiveMessageInSQS(String filter, String queue, String region) throws Throwable {
        sqsSteps.receiveSQSMessage(filter, queue, region);
    }

}