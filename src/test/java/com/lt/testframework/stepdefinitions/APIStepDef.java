/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * User: Lesia Topol
 * Date: 5/26/2023
 * All rights reserved
 */

package com.lt.testframework.stepdefinitions;

import com.lt.testframework.stepimpl.APIStepImpl;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class APIStepDef {

    @Steps
    private APIStepImpl apiSteps;

    @Given("REST {word} with defaults")
    public void readRestRequestDefaults(String fileName) throws Throwable {
        apiSteps.readRestDefaults(fileName);
    }

    @Given("REST {word} is parametrized")
    public void readRestRequestParams(String fileName, DataTable dataTable) throws Throwable {
        apiSteps.customizeRestEndpoint(fileName, dataTable);
    }

    @Given("REST {word} with parametrized$ body")
    public void mapYamlWithUpdatedBody(String fileName, DataTable dataTable) throws Throwable {
        apiSteps.readRestDefaults(fileName);
        apiSteps.customizeRestRequestBody(dataTable);
    }

    @Given("REST has custom headers")
    public void updatedHeaders(DataTable dataTable) throws Throwable {
        apiSteps.customizeRestHeaders(dataTable);
    }

    @When("system triggers endpoint")
    public void triggerEndpoint() throws Throwable {
        apiSteps.executeRestRequest();
    }

    @Then("^verify REST response (.*) is (.*)$")
    public void verifyRestResponseStatus(String responseAttribute, String expectedValue) throws Throwable {
        String actualValue = "";
        switch (responseAttribute) {
            case "status line":
                actualValue = String.valueOf(apiSteps.getResponse().getStatusLine());
                break;
            case "status code":
                actualValue = String.valueOf(apiSteps.getResponse().getStatusCode());
                break;
        }
        assertEquals(expectedValue, actualValue, "Expected and actual values are not equal");
    }
}