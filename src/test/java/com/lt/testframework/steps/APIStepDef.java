/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * User: Lesia Topol
 * Date: 7/10/2022
 * All rights reserved
 */

package com.lt.testframework.steps;

import com.lt.testframework.APIStepImpl;
import com.lt.testframework.utils.JsonUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.util.Map;

public class APIStepDef {

    @Steps
    APIStepImpl steps;

    @Given("REST {word} has default attributes")
    public void readDefaultRestRequest(String fileName) throws Throwable {
        steps.readRestDefaults(fileName);
    }

    @Given("REST {word} is parametrized")
    public void readRestRequestParams(String fileName, DataTable dataTable) throws Throwable {
        steps.readRestParametrized(fileName, dataTable);
    }

    @Given("REST {word} with parametrized$ body")
    public void mapYamlWithUpdatedBody(String fileName, DataTable dataTable) throws Throwable {
        steps.readRestDefaults(fileName);
        steps.parametrizedRequestBody(dataTable);
    }

    @Given("REST has custom headers")
    public void updatedHeaders(DataTable dataTable) throws Throwable {
        steps.addHeaders(dataTable);
    }

    @When("system triggers endpoint")
    public void triggerEndpoint() throws Throwable {
        steps.executeRest();
    }

    @Then("verify REST response attributes as expected")
    public void verifyResponseBody(DataTable dataTable) throws Throwable {
        Map<String, String> attributesToValidate = dataTable.asMap(String.class, String.class);
        attributesToValidate.forEach((k, v) -> {
            String actualValue = JsonUtils.getJsonValue(steps.getResponse().getBody().asString(), k).toString();
            Assert.assertEquals(v, actualValue.trim());
        });
    }

    @Then("^verify REST response (.*) is (.*)$")
    public void verifyStatusLine(String responseAttribute, String expectedValue) throws Throwable {
        String actualValue = "";
        switch (responseAttribute) {
            case "status line":
                actualValue = String.valueOf(steps.getResponse().getStatusLine());
                break;
            case "status code":
                actualValue = String.valueOf(steps.getResponse().getStatusCode());
                break;
        }
        Assert.assertEquals(expectedValue, actualValue);
    }
}