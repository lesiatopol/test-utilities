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

import com.lt.testframework.stepimpl.SNSStepImpl;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import net.thucydides.core.annotations.Steps;

public class SNSStepDef {
    @Steps
    private SNSStepImpl snsSteps;

    @Given("^system publishes SNS event (.*) to (.*) in (.*) region$")
    public void sendEventToSNS(String inputJsonFile, String topic, String region, DataTable dataSet) throws Throwable {
        snsSteps.publishEvent(inputJsonFile, topic, region, dataSet);
    }
}