package com.demo.sqs;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;

import java.util.List;

public class SQSReadMessages {
	private static String accessKey =  "";
	private static String secretKey = "";
	
    public static void main(String[] args) {
    	AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
    	

        SqsClient sqsClient = SqsClient.builder().credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.AP_SOUTHEAST_1)
                .build();
    	
        String queue = "dev-sqs";
        readMessages(sqsClient, queue);
        sqsClient.close();
    }

    public static void readMessages(SqsClient sqsClient, String queueUrl) {
        try {
            ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .maxNumberOfMessages(5)
                    .build();
            List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();
            messages.forEach(message -> System.out.println(message.body()));
        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}
