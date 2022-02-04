package com.demo.sqs;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.Arrays;
import java.util.Collection;

public class SQSSendMessagesToFIFOQueue {
    public static void main(String[] args) {
        SqsClient sqsClient = SqsClient.builder()
                .region(Region.US_WEST_2)
                .build();
        String queue = "https://sqs.us-west-2.amazonaws.com/{account_id}/My-FIFO-Queue.fifo";
        sendSingleFifoMessage(sqsClient, queue);
        sendBatchFIFOMessages(sqsClient, queue);
        sqsClient.close();
    }

    public static void sendSingleFifoMessage(SqsClient sqsClient, String queueUrl) {
        try {
            sqsClient.sendMessage(SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody("Hello world from Java!")
                    .messageGroupId("12345")
                    .messageDeduplicationId("123")
                    .build());
            System.out.println("Message has been sent successfully");
        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    public static void sendBatchFIFOMessages(SqsClient sqsClient, String queueUrl) {

        try {
            Collection<SendMessageBatchRequestEntry> messages = Arrays.asList(
                    SendMessageBatchRequestEntry.builder().id("id-1").messageBody("Hello 1")
                            .messageGroupId("12345").messageDeduplicationId("123").build(),
                    SendMessageBatchRequestEntry.builder().id("id-2").messageBody("Hello 2")
                            .messageGroupId("12345").messageDeduplicationId("123").build(),
                    SendMessageBatchRequestEntry.builder().id("id-3").messageBody("Hello 3").
                            messageGroupId("12345").messageDeduplicationId("123").build()
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
