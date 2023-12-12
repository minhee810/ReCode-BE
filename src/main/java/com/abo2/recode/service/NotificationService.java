package com.abo2.recode.service;

import com.abo2.recode.domain.notification.NotificationRepository;
import com.abo2.recode.domain.notification.Notifications;
import com.abo2.recode.domain.studymember.StudyMember;
import com.abo2.recode.domain.studymember.StudyMemberRepository;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserRepository;
import com.abo2.recode.dto.notification.NotificationReqDto;
import com.abo2.recode.dto.notification.NotificationRespDto;
import com.abo2.recode.handler.ex.CustomApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final UserRepository userRepository;
    private final StudyMemberRepository studyMemberRepository;
    public final NotificationRepository notificationRepository;

    @Transactional
    public void sendNotification(Long studyMemberId, Long userId, String lambdaFunctionUrl) {

        // RestTemplate 객체 생성 HTTP 요청을 보내고 응답을 받는 데 사용되는 스프링 클래스 이를 사용해서 람다 함수로 HTTP POST 요청을 보냄
        RestTemplate restTemplate = new RestTemplate();

        // 요청 헤더 설정  ContentType 을  json으로  설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // 람다함수 호출 - exchange 메서드 람다함수에 post요청
        ResponseEntity<String> lambdaResponse = restTemplate.exchange(
                lambdaFunctionUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        logger.info("lambdaResponse {}: ", lambdaResponse);

        // Lambda 함수 응답 처리
        if (lambdaResponse.getStatusCode().is2xxSuccessful()) {

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                NotificationRespDto.NotificationRespSaveDto notificationRespDto = objectMapper.readValue(lambdaResponse.getBody(), NotificationRespDto.NotificationRespSaveDto.class);

                String message = notificationRespDto.getMessage();
                String messageId = notificationRespDto.getMessageId();
                String type = "스터디 신청 승인 여부 알림";

                // 메시지 저장하는 부분
                saveNotification(studyMemberId, userId, message, messageId, type);

                logger.info("### message ### {}", message);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        } else {
            throw new CustomApiException("Lambda 함수 호출 실패 ");
        }
    }

    // 메시지 저장
    @Transactional
    public void saveNotification(Long studyMemberId, Long userId, String message, String messageId, String type) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException("존재하지 않는 userId"));

        StudyMember studyMember = studyMemberRepository.findById(studyMemberId)
                .orElseThrow(() -> new CustomApiException("존재하지 않는 studyRoomId"));

        NotificationRespDto.NotificationRespSaveDto notificationRespSaveDto = new NotificationRespDto.NotificationRespSaveDto();
        notificationRespSaveDto.setUserId(user);
        notificationRespSaveDto.setStudyMemberId(studyMember);
        notificationRespSaveDto.setMessage(message);
        notificationRespSaveDto.setReadStatus(false);
        notificationRespSaveDto.setMessageId(messageId);
        notificationRespSaveDto.setType(type);

        logger.info("notificationRespDto = " + notificationRespSaveDto);
        notificationRepository.save(notificationRespSaveDto.toEntity());
    }

    @Transactional
    public List<NotificationRespDto.NotificationListRespDto> getNotificationsByUserId(Long userId) {
        List<Notifications> notifications = notificationRepository.findByUserId(userId);

        logger.info("notifications {} ", notifications);

        if (notifications.isEmpty()) {
            throw new CustomApiException("알림이 존재하지 않습니다.");
        }
        return notifications.stream()
                .map(NotificationRespDto.NotificationListRespDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public NotificationRespDto.MarkAsReadRespDto markAsRead(NotificationReqDto.MarkAsReadReqDto markAsReadReqDto) {

        // 1. 해당 사용자의 아이디로 알림 목록을 가져옴.
        // 2. 사용자의 알림 읽을 상태를 dto 로 받아와서 엔티티의 값을 바꾸어 저장해줘야 함.
        logger.info("markAsReadReqDto.getId() {}" , markAsReadReqDto.getId());
        logger.info("readStatus {} : ", markAsReadReqDto.isReadStatus());
        Optional<Notifications> optionalNotifications = notificationRepository.findById(markAsReadReqDto.getId());

        if (optionalNotifications.isPresent()) {
            Notifications notifications = optionalNotifications.get();

            // 값 수정
            notifications.updateReadStatus(markAsReadReqDto.isReadStatus());
            // 반환 Dto 생성
            return new NotificationRespDto.MarkAsReadRespDto(notifications);

        }else {
            throw new CustomApiException("해당 알림이 존재하지 않습니다.");
        }
                
    }

    // 알림 삭제
    @Transactional
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}
