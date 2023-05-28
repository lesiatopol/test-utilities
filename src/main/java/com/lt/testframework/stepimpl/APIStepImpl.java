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

package com.lt.testframework.stepimpl;

import com.lt.testframework.config.AppContextConfig;
import com.lt.testframework.datamanager.DataStorage;
import com.lt.testframework.apimanager.RequestFormatter;
import com.lt.testframework.apimanager.RequestMetadata;
import io.cucumber.datatable.DataTable;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@ContextConfiguration(classes = {AppContextConfig.class})
public class APIStepImpl {
    protected static final Logger logger = LoggerFactory.getLogger(APIStepImpl.class);
    protected Response response;
    protected ValidatableResponse validatableResponse = null;
    @Autowired
    private RequestFormatter requestFormatter;
    @Autowired
    private RequestMetadata requestMetadata;

    @Step
    public void readRestDefaults(String resourceName) {
        requestMetadata = requestFormatter.mapRequestDataDefaults(resourceName);
    }

    @Step
    public void customizeRestEndpoint(String resourceName, DataTable dataTable) {
        requestMetadata = requestFormatter.mapRequestDataDefaults(resourceName);
        requestMetadata = requestFormatter.customizeRequestData("endpoint", getDataTable(dataTable));
    }

    @Step
    public void customizeRestRequestBody(DataTable dataTable) {
        requestMetadata = requestFormatter.customizeRequestData("body", getDataTable(dataTable));
    }

    @Step
    public void customizeRestHeaders(DataTable dataTable) {
        requestMetadata = requestFormatter.customizeRequestData("headers", getDataTable(dataTable));
    }

    @Step
    public void executeRestRequest() {
        try {
            validatableResponse = sendRequest(requestMetadata).then().log().all().extract().response().then();
            logger.info("Response Returned in {} sec", response.getTimeIn(TimeUnit.SECONDS));
            DataStorage.getInstance().setNewData("BODY_TO_VALIDATE", response.getBody().asString());
            assertNotNull(validatableResponse, "Rest response is null");
        } catch (NullPointerException e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            fail("NullPointerException exception occured during fetching response, \nStatus Code : " + response.statusCode());
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            fail("NullPointerException exception occured during fetching response \n" + e.getMessage());
        }
    }

    private Response sendRequest(RequestMetadata requestMetadata) {
        logger.info("Sending REST request");
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
                    response = requestSpecification.delete(requestMetadata.getEndpoint());
                    break;
                default:
                    logger.info("No REST was invoked, method was undefined");
                    break;
            }
        } catch (Exception e) {
            logger.info(e.toString());
        }
        return response;
    }

    private static Map<String, String> getDataTable(DataTable dataTable) {
        return dataTable.asMap(String.class, String.class);
    }

    public Response getResponse() {
        return response;
    }
}
