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
import com.lt.testframework.stepimpl.StepFunctionStepImpl;
import io.cucumber.java.en.Given;
import net.thucydides.core.annotations.Steps;

public class StepFunctionStepDef {
    @Steps
    private StepFunctionStepImpl stepFunctionSteps;

    @Given("^system executes Step Function (.*) in (.*) region with (.*) input that has data$")
    public void runStepFunction(String stepFunctionName, String region, String inoutJsonFile, DataTable dataSet) throws Throwable {
        stepFunctionSteps.executeStepFunction(stepFunctionName, region, inoutJsonFile, dataSet);
    }
}