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

package com.lt.testframework;

import com.lt.testframework.utils.RequestFormatter;
import com.lt.testframework.utils.RequestMetadata;
import io.cucumber.datatable.DataTable;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class APIStepImpl {
    protected static final Logger logger = LoggerFactory.getLogger(APIStepImpl.class);
    protected static RequestMetadata requestMetadata = null;
    protected static RequestFormatter requestFormatter = new RequestFormatter();
    protected static Response response;
    protected static BaseTest baseTest = BaseTest.getInstance();

    @Step
    public static void readRestDefaults(String resourceName) {
        requestMetadata = requestFormatter.readDefaults(resourceName);
    }

    @Step
    public static void readRestParametrized(String resourceName, DataTable dataTable) {
        requestMetadata = requestFormatter.readDefaults(resourceName);
        requestMetadata = requestFormatter.updateREST("endpoint", getDataTable(dataTable));
    }

    @Step
    public static void parametrizedRequestBody(DataTable dataTable) {
        requestMetadata = requestFormatter.updateREST("body", getDataTable(dataTable));
    }

    @Step
    public static void addHeaders(DataTable dataTable) {
        requestMetadata = requestFormatter.updateREST("headers", getDataTable(dataTable));
    }

    @Step
    public static void executeRest() {
        try {
            sendRequest(requestMetadata).log().all().extract().response();
            logger.info("Response Returned in {} sec", response.getTimeIn(TimeUnit.SECONDS));
        } catch (NullPointerException e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            Assert.fail("NullPointerException exception occured during fetching response, \nStatus Code : " + response.statusCode());
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            Assert.fail("NullPointerException exception occured during fetching response \n" + e.getMessage());
        }
    }

    private static ValidatableResponse sendRequest(RequestMetadata requestMetadata) {
        logger.info("Sending REST request");
        ValidatableResponse validatableResponse = null;
        RequestSpecification requestSpecification1 = new RequestSpecBuilder().build();
        try {
            RequestSpecification requestSpecification = SerenityRest.given().relaxedHTTPSValidation()
                    .spec(requestSpecification1).log().all().when()
                    .baseUri(requestMetadata.getBaseURL())
                    .body(requestMetadata.getPayload())
                    .headers(requestMetadata.getHeaders());
            switch (requestMetadata.getMethod()) {
                case "GET":
                    response = requestSpecification.get(requestMetadata.getEndpoint());
                    break;
                case "POST":
                    response = requestSpecification.post(requestMetadata.getEndpoint());
                    break;
                case "PUT":
                    response = requestSpecification.put(requestMetadata.getEndpoint());
                    break;
                case "PATCH":
                    response = requestSpecification.patch(requestMetadata.getEndpoint());
                    break;
                case "DELETE":
                    validatableResponse = requestSpecification.delete(requestMetadata.getEndpoint()).then();
                    break;
                default:
                    logger.info("No REST was invoked, method was undefined");
                    break;
            }
            if (response != null) {
                validatableResponse = response.then();
            }
        } catch (Exception e) {
            logger.info(e.toString());
        }
        return validatableResponse;
    }

    private static Map<String, String> getDataTable(DataTable dataTable) {
        return dataTable.asMap(String.class, String.class);
    }

    public static Response getResponse() {
        return response;
    }
}
