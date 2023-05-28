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
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.ListTopicsRequest;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SNSManager {

    protected static final Logger logger = LoggerFactory.getLogger(SNSManager.class);
    @Value("${aws.default.region}")
    private String awsRegion;
    @Autowired
    private CredentialsProvider credentialsProvider;

    private AmazonSNS snsClient;
    private PublishResult publishResult;

    public SNSManager() {
    }

    public void buildClient() {
        logger.info("SNS client is in {} region", awsRegion);
        this.snsClient = AmazonSNSClientBuilder.standard()
                .withCredentials(credentialsProvider.getAwsCredentialsProvider())
                .withRegion(awsRegion)
                .withClientConfiguration(new ClientConfiguration())
                .build();
    }

    public PublishResult publishEvent(String topic, String eventRequest) {
        PublishRequest publishRequest = new PublishRequest()
                .withTopicArn(getTopicArn(topic))
                .withMessage(eventRequest)
                .withMessageGroupId(UUID.randomUUID().toString())
                .withMessageDeduplicationId(UUID.randomUUID().toString());
        publishResult = snsClient.publish(publishRequest);
        return publishResult;
    }

    private String getTopicArn(String topic) {
        return snsClient.listTopics(new ListTopicsRequest()).getTopics().stream()
                .filter(topicList -> topicList.getTopicArn().contains(topic))
                .map(Topic::getTopicArn)
                .findAny()
                .orElse("");
    }

    public PublishResult getPublishResult() {
        return publishResult;
    }

    public void setAwsRegion(String awsRegion) {
        this.awsRegion = awsRegion;
    }
}
