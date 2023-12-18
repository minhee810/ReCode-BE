package com.abo2.recode.dto.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class ChatResDto {

    @Getter
    @Setter
    public static class ChatListDto{

        @NotEmpty
        private Long chatRoomId;

        @NotEmpty
        private List<String> usernameList;

        @NotEmpty
        private String title;

        @NotEmpty
        private String lastMessage;

        @NotEmpty
        private String createdBy;

        @Builder
        public ChatListDto(Long chatRoomId, List<String> usernameList, String title, String lastMessage,String createdBy) {
            this.chatRoomId = chatRoomId;
            this.usernameList = usernameList;
            this.title = title;
            this.lastMessage = lastMessage;
            this.createdBy = createdBy;
        }
    }

    @Getter
    @Setter
    public static class ChatDeleteResDto{

        @NotEmpty
        private Long id; //ChatRoom 일련번호

        @NotEmpty
        private String title;

        @Builder
        public ChatDeleteResDto(Long id, String title) {
            this.id = id;
            this.title = title;
        }
    }
}
