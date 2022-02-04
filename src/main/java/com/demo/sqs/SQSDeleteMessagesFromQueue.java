package com.demo.sqs;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.SqsException;

import java.util.List;

public class SQSDeleteMessagesFromQueue {

    public static void deleteMessages(SqsClient sqsClient, String queueUrl, List<Message> messages) {
        try {
            for (Message message : messages) {
                System.out.println("message Body:: " + message.body());
                DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .receiptHandle(message.receiptHandle())
                        .build();
                sqsClient.deleteMessage(deleteMessageRequest);
            }
        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}
