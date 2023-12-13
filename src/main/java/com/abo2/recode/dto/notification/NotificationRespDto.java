package com.abo2.recode.dto.notification;

import com.abo2.recode.domain.notification.Notifications;
import com.abo2.recode.domain.studymember.StudyMember;
import com.abo2.recode.domain.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

public class NotificationRespDto {

    @Getter
    @Setter
    public static class NotificationRespSaveDto {

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
                    .message(message)
                    .messageId(messageId)
                    .readStatus(readStatus)
                    .type(type)
                    .build();
        }

    }


    @Getter
    @Setter
    public static class NotificationListRespDto{

        @NotEmpty
        private Long id;
        @NotEmpty
        private String userName;
        @NotEmpty
        private String studyName;
        @NotEmpty
        private String message;
        @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm")
        private LocalDateTime createdAt;
        @NotEmpty
        private boolean readStatus;
        @NotEmpty
        private String type;


        public NotificationListRespDto(Notifications notifications) {
            this.id = notifications.getId();
            this.userName = notifications.getUser().getNickname();
            this.studyName = notifications.getStudyMember().getStudyRoom().getStudyName();
            this.message = notifications.getMessage();
            this.createdAt = notifications.getCreatedAt();
            this.readStatus = notifications.isReadStatus();
            this.type = notifications.getType();
        }

    }

    @Getter
    @Setter
    public static class MarkAsReadRespDto{
        @NotEmpty
        private Long id;
        @NotEmpty
        private boolean readStatus;

        public MarkAsReadRespDto(Notifications notifications){
            this.id = notifications.getId();
            this.readStatus = notifications.isReadStatus();
        }
    }


}
