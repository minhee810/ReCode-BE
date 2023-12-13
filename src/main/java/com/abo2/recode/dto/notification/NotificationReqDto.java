package com.abo2.recode.dto.notification;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class NotificationReqDto {

    @Getter
    @Setter
    public static class NotificationMessageReqDto{

        private String message;
        private String messageId;

    }

    @Getter
    @Setter
    public static class MarkAsReadReqDto{
        @NotEmpty
        private Long id;
        @NotEmpty
        private boolean readStatus;
    }



}
