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

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SQSManager {

    protected static final Logger logger = LoggerFactory.getLogger(SQSManager.class);
    @Value("${aws.default.region}")
    private String awsRegion;
    @Autowired
    private CredentialsProvider credentialsProvider;

    private AmazonSQS sqsClient;
    private SendMessageResult sendMessageResult;

    public SQSManager() {
    }

    public void buildClient() {
        logger.info("SQS client is in {} region", awsRegion);
        this.sqsClient = AmazonSQSClientBuilder.standard()
                .withCredentials(credentialsProvider.getAwsCredentialsProvider())
                .withRegion(awsRegion)
                .build();
    }

    public SendMessageResult sendMessage(String queue, String msgRequest) {
        SendMessageRequest sendMsgRequest = new SendMessageRequest()
                .withQueueUrl(queue)
                .withMessageBody(msgRequest);
        sendMsgRequest.setMessageDeduplicationId(UUID.randomUUID().toString());
        sendMsgRequest.setMessageGroupId(UUID.randomUUID().toString());
        sendMessageResult = sqsClient.sendMessage(sendMsgRequest);
        return sendMessageResult;
    }

    public String receiveMessage(String queue, String filterValue) {
        ReceiveMessageRequest receiveMsgRequest = new ReceiveMessageRequest()
                .withQueueUrl(queue)
                .withWaitTimeSeconds(20)
                .withVisibilityTimeout(30)
                .withMaxNumberOfMessages(10);
        List<Message> messages = sqsClient.receiveMessage(receiveMsgRequest).getMessages();
        return messages.stream()
                .filter(sm -> sm.getBody().contains(filterValue))
                .map(Message::getBody)
                .findAny()
                .orElse("");
    }

    public void deleteMessage(String queue, Message message) {
        sqsClient.deleteMessage(new DeleteMessageRequest()
                .withQueueUrl(queue)
                .withReceiptHandle(message.getReceiptHandle()));
    }

    public SendMessageResult getSendMessageResult() {
        return sendMessageResult;
    }

    public void setAwsRegion(String awsRegion) {
        this.awsRegion = awsRegion;
    }

    private String getQueueUrl(String queue) {
        return sqsClient.listQueues(new ListQueuesRequest(queue)).getQueueUrls().get(0);
    }
}
