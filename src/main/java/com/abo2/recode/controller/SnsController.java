package com.abo2.recode.controller;

import com.abo2.recode.config.AWSConfig;
import com.abo2.recode.service.CredentialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SnsController {

    AWSConfig awsConfig;
    CredentialService credentialService;

    public SnsController(AWSConfig awsConfig, CredentialService credentialService) {
        this.awsConfig = awsConfig;
        this.credentialService = credentialService;
    }


    private ResponseStatusException getResponseStatusException(SnsResponse response) {
        return new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, response.sdkHttpResponse().statusText().get()
        );
    }

    @PostMapping("/createTopic")
    public ResponseEntity<String> createTopic(@RequestParam final String topicName) {
        final CreateTopicRequest createTopicRequest = CreateTopicRequest.builder()
                .name(topicName)
                .build();
        SnsClient snsClient = credentialService.getSnsClient();
        final CreateTopicResponse createTopicResponse = snsClient.createTopic(createTopicRequest);

        if (!createTopicResponse.sdkHttpResponse().isSuccessful()) {
            throw getResponseStatusException(createTopicResponse);
        }
        log.info("topic name = " + createTopicResponse.topicArn());
        log.info("topic list = " + snsClient.listTopics());
        snsClient.close();
        return new ResponseEntity<>("TOPIC CREATING SUCCESS", HttpStatus.OK);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestParam final String endpoint, @RequestParam final String topicArn) {
        final SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                .protocol("https")
                .topicArn(topicArn)
                .endpoint(endpoint)
                .build();
        SnsClient snsClient = credentialService.getSnsClient();
        final SubscribeResponse subscribeResponse = snsClient.subscribe(subscribeRequest);

        if (!subscribeResponse.sdkHttpResponse().isSuccessful()) {
            throw getResponseStatusException(subscribeResponse);
        }
        log.info("topicARN to subscribe = " + subscribeResponse.subscriptionArn());
        log.info("subscription list = " + snsClient.listSubscriptions());
        snsClient.close();
        return new ResponseEntity<>("TOPIC SUBSCRIBE SUCCESS", HttpStatus.OK);
    }
    @PostMapping("/send-sms")
    public ResponseEntity<String> sendSms(@RequestParam String phoneNumber, @RequestParam String message) {
        String messageId = credentialService.sendSmsMessage(phoneNumber, message);
        return ResponseEntity.ok("메시지 전송 완료, MessageID: " + messageId);
    }
}
