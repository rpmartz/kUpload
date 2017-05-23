package com.ryanpmartz.kupload.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Value("${aws.access-key}")
    private String awsAccessKey;

    @Value("${aws.secret-key}")
    private String awsSecretKey;

    @Bean
    public AWSCredentials awsCredentials() {
        return new BasicAWSCredentials(awsAccessKey, awsSecretKey);
    }

    @Bean
    @SuppressWarnings("deprecation")
    public AmazonS3Client transferManager() {
        AWSCredentialsProvider staticProvider = new AWSStaticCredentialsProvider(awsCredentials());
        return new AmazonS3Client(staticProvider);
    }
}
