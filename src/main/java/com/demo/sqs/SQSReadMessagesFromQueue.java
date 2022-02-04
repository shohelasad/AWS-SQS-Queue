package com.demo.sqs;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;
import java.util.List;

public class SQSReadMessagesFromQueue {
    public static void main(String[] args) {
        SqsClient sqsClient = SqsClient.builder()
                .region(Region.US_WEST_2)
                .build();
        String queue = "https://sqs.us-west-2.amazonaws.com/{account_id}/My-FIFO-Queue.fifo";
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
