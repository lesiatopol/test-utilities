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

import com.lt.testframework.datamanager.DataStorage;
import com.lt.testframework.utils.JsonUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommonValidationStepDef {
    @Then("verify response attributes are as expected")
    public void verifyResponse(DataTable dataTable) throws Throwable {
        Map<String, String> attributesToValidate = dataTable.asMap(String.class, String.class);
        attributesToValidate.forEach((attributeToValidate, expectedValue) -> {
            String actualValue = JsonUtils.getJsonValue(DataStorage.getInstance().getExistingData("BODY_TO_VALIDATE"), attributeToValidate).toString();
            assertEquals(expectedValue, actualValue, "Response body does not contain expected value");
        });
    }
}