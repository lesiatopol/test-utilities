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

import com.lt.testframework.stepimpl.S3StepImpl;
import io.cucumber.java.en.Given;
import net.thucydides.core.annotations.Steps;

public class S3StepDef {
    @Steps
    private S3StepImpl s3Steps;

    @Given("^system puts File (.*) to (.*) / (.*) in (.*) region$")
    public void putFileIntoBucket(String inputFile, String bucket, String folder, String region) throws Throwable {
        s3Steps.putFileIntoBucket(inputFile, bucket, folder, region);
    }

    @Given("^system gets File (.*) in (.*) / (.*) in (.*) region$")
    public void receiveMessageInSQS(String inputFile, String bucket, String folder, String region) throws Throwable {
        s3Steps.getFileFromBucket(inputFile,bucket, folder,region);
    }

}