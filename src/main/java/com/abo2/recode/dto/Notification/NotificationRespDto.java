package com.abo2.recode.dto.Notification;

import com.abo2.recode.domain.notification.Notifications;
import com.abo2.recode.domain.studymember.StudyMember;
import com.abo2.recode.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationRespDto {

    private User userId;
    private StudyMember studyMemberId;
    private String message;
    private String messageId;

    private LocalDateTime createAt;

    private boolean readStatus;

    private String type;

    public Notifications toEntity() {
        return Notifications.builder()
                .user(userId)
                .studyMember(studyMemberId)
                .readStatus(false)
                .message(message)
                .messageId(messageId)
                .type(type)
                .build();

    }


}
