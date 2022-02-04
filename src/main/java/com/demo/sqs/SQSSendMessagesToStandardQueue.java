package com.demo.sqs;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;
import java.util.*;


public class SQSSendMessagesToStandardQueue {
    
    private static String accessKey =  "";
	private static String secretKey = "";
	
    public static void main(String[] args) {
    	AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
    	
        SqsClient sqsClient = SqsClient.builder().credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.AP_SOUTHEAST_1)
                .build();
    	
        String queue = "https://sqs.ap-southeast-1.amazonaws.com/{account_id}/mfi-jcf-dev";
        sendSingleMessage(sqsClient, queue);
        sendBatchMessages(sqsClient, queue);
        sqsClient.close();
    }

    public static void sendSingleMessage(SqsClient sqsClient, String queueUrl) {
        try {
            sqsClient.sendMessage(SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody("Hello world from Java!")
                    .delaySeconds(10)
                    .build());
            System.out.println("Message has been sent successfully");
        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
    public static void sendBatchMessages(SqsClient sqsClient, String queueUrl) {

        try {
            Collection<SendMessageBatchRequestEntry> messages = Arrays.asList(
                    SendMessageBatchRequestEntry.builder().id("id-1").messageBody("Hello 1").build(),
                    SendMessageBatchRequestEntry.builder().id("id-2").messageBody("Hello 2").build(),
                    SendMessageBatchRequestEntry.builder().id("id-3").messageBody("Hello 3").build()
            );
            SendMessageBatchRequest sendMessageBatchRequest = SendMessageBatchRequest.builder()
                    .queueUrl(queueUrl)
                    .entries(messages)
                    .build();
            sqsClient.sendMessageBatch(sendMessageBatchRequest);
            System.out.println("Messages has been sent successfully as a batch");
        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }
}
