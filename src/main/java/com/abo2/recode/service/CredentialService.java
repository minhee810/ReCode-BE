package com.abo2.recode.service;

import com.abo2.recode.config.AWSConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

/*
    아마존 SNS 서비스로의 접근 허용
    Aws Credential 을 통해, AWS 가 발급한 암호화 키를 제출하는 것으로 서비스에 대한 제어 권한이 생긴다
 */
@Service
@RequiredArgsConstructor
public class CredentialService {

    private final AWSConfig awsConfig;

    private final SnsClient snsClient;


    public AwsCredentialsProvider getAwsCredentials(String accessKeyID, String secretAccessKey) {
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKeyID, secretAccessKey);
        return () -> awsBasicCredentials;
    }

    /*
        SnsClient 는 해당 자격을 주입받고 내가 지정한 리전에 명령을 내려주는 역할
        앞으로 하는 모든 서비스의 명령은 SnsClient 가 전달해준다
     */
    public SnsClient getSnsClient() {
        return SnsClient.builder()
                .credentialsProvider(
                        getAwsCredentials(awsConfig.getAwsAccessKey(), awsConfig.getAwsSecretKey())
                ).region(Region.of(awsConfig.getAwsRegion()))
                .build();
    }

    public String sendSmsMessage(String phoneNumber, String message) {
        PublishRequest request = PublishRequest.builder()
                .message(message)
                .phoneNumber(phoneNumber)
                .build();
        PublishResponse response = snsClient.publish(request);
        return response.messageId();
    }
}
