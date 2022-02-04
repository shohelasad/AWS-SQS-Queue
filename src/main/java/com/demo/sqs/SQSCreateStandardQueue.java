package com.demo.sqs;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;
import software.amazon.awssdk.services.sqs.model.SqsException;

public class SQSCreateStandardQueue {
	
	private static String accessKey = "";
	private static String secretKey = "";
	
    public static void main(String[] args) {
    	AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
    	
        String queueName = "mfi-jcfs-dev";
        SqsClient sqsClient = SqsClient.builder().credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.AP_SOUTHEAST_1)
                .build();
        String queueUrl= createStandardQueue(sqsClient, queueName);
        System.out.println("Queue URL : "+queueUrl);
        sqsClient.close();
    }

    public static String createStandardQueue(SqsClient sqsClient,String queueName) {
        try {
            CreateQueueRequest cqr = CreateQueueRequest.builder()
                    .queueName(queueName)
                    .build();

            sqsClient.createQueue(cqr);

            GetQueueUrlResponse getQueueUrlResponse =
                    sqsClient.getQueueUrl(GetQueueUrlRequest.builder().queueName(queueName).build());
            return getQueueUrlResponse.queueUrl();
        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return null;
    }
}
