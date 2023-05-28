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

package com.lt.testframework.awsmanager;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Component
public class S3Manager {

    protected static final Logger logger = LoggerFactory.getLogger(S3Manager.class);
    @Value("${aws.default.region}")
    private String awsRegion;
    @Autowired
    private CredentialsProvider credentialsProvider;
    private AmazonS3 s3Client;
    private PutObjectResult putObjectResult;

    public S3Manager() {
    }

    public void buildClient() {
        logger.info("S3 client is in {} region", awsRegion);
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(credentialsProvider.getAwsCredentialsProvider())
                .withRegion(awsRegion)
                .withClientConfiguration(new ClientConfiguration())
                .build();
    }

    public PutObjectResult putFileIntoS3Bucket(String bucket, String folder, File fileToUpload) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket + folder, fileToUpload.getName(), fileToUpload);
        putObjectResult = s3Client.putObject(putObjectRequest);
        return putObjectResult;
    }

    public String getObjectFromS3Bucket(String bucket, String folder, String objectKey) {
        boolean isFileFound = false;
        ListObjectsV2Request listObjectsV2Request = new ListObjectsV2Request()
                .withBucketName(getBucket(bucket))
                .withPrefix(folder)
                .withDelimiter("/");
        String object = "";
        while (!isFileFound) {
            object = s3Client.listObjectsV2(listObjectsV2Request)
                    .getObjectSummaries().stream()
                    .filter(o -> o.getKey().contains(objectKey))
                    .map(S3ObjectSummary::getKey)
                    .findAny()
                    .orElse("");
            String token = s3Client.listObjectsV2(listObjectsV2Request).getContinuationToken();
            if (object.equals("") && token != null) {
                listObjectsV2Request.setContinuationToken(token);
            } else {
                isFileFound = true;
            }
        }
        return object;
    }

    public PutObjectResult getPutObjectResult() {
        return putObjectResult;
    }

    public void setAwsRegion(String awsRegion) {
        this.awsRegion = awsRegion;
    }

    private String getBucket(String bucket) {
        return s3Client.listBuckets(new ListBucketsRequest()).stream()
                .filter(b -> b.getName().contains(bucket))
                .map(Bucket::getName)
                .findAny()
                .orElse("");
    }

}
