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

import com.lt.testframework.stepimpl.LambdaStepImpl;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.thucydides.core.annotations.Steps;

public class LambdaStepDef {
    @Steps
    private LambdaStepImpl lambdaSteps;

    @Given("^system triggers Lambda (.*) in (.*) region with (.*) that has data$")
    public void invokeLambda(String lambda, String region, String inputJsonFile, DataTable dataSet) throws Throwable {
        lambdaSteps.readLambdaResponse(lambda, inputJsonFile, region, dataSet);
    }

    @Then("^validate Lambda status code is (.*)$")
    public void validateLambda(String expectedStatusCode) throws Throwable {
        lambdaSteps.checkLambdaStatusCode(expectedStatusCode);
    }
}