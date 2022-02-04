package com.demo.sqs;



import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.ListQueuesRequest;
import software.amazon.awssdk.services.sqs.model.ListQueuesResponse;
import software.amazon.awssdk.services.sqs.model.SqsException;

public class SQSListQueues {
	
	private static String accessKey = "";
	private static String secretKey = "";
	
    public static void main(String[] args) {
    	
    	AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);

        SqsClient sqsClient = SqsClient.builder().credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.AP_SOUTHEAST_1)
                .build();

        listAllQueues(sqsClient);
        sqsClient.close();
    }

    public static void listAllQueues(SqsClient sqsClient) {
        try {
            ListQueuesRequest listQueuesRequest = ListQueuesRequest.builder().build();
            ListQueuesResponse listQueuesResponse = sqsClient.listQueues(listQueuesRequest);
            listQueuesResponse.queueUrls().forEach(System.out::println);
        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}
