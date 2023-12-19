package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.notification.NotificationReqDto;
import com.abo2.recode.dto.notification.NotificationRespDto;
import com.abo2.recode.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {
    private final NotificationService notificationService;

    // 알림 목록 조회
    @GetMapping("/user")
    public ResponseEntity<?> getAllNotification(@AuthenticationPrincipal @Valid LoginUser loginUser) {
        List<NotificationRespDto.NotificationListRespDto> notificationListRespDtoList = notificationService.getNotificationsByUserId(loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "알림 목록 조회 성공", notificationListRespDtoList), HttpStatus.OK);
    }

    // 알림 읽음 처리
    @PostMapping("/mark-as-read")
    public ResponseEntity<?> markAsRead(@AuthenticationPrincipal @Valid LoginUser loginUser,
                                        @RequestBody NotificationReqDto.MarkAsReadReqDto markAsReadReqDto) {

        NotificationRespDto.MarkAsReadRespDto readStatusDto = notificationService.markAsRead(markAsReadReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "알림 읽음 처리 완료", readStatusDto), HttpStatus.OK);
    }

    // 알림 삭제
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<?> deleteMessage(@PathVariable("notificationId") Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return new ResponseEntity<>(new ResponseDto<>(1, "알림 삭제 처리 완료", null), HttpStatus.OK);
    }

}



