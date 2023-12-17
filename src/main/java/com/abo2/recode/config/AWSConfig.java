package com.abo2.recode.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

@Getter
@Configuration
public class AWSConfig {

    @Value("${sns.topic.arn}")
    private String snsTopicARN;

    @Value("${cloud.aws.credentials.access-key}")
    private String awsAccessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String awsSecretKey;

    @Value("${cloud.sns.region}")
    private String awsRegion;

    @Bean
    public SnsClient snsClient() {
        return SnsClient.builder()
                .region(Region.AP_NORTHEAST_1) // 도쿄 리전 설정
                .build();
    }
}
