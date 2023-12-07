package com.abo2.recode.dto.chat;

import com.abo2.recode.domain.user.User;
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

        @Builder
        public ChatListDto(Long chatRoomId, List<String> usernameList, String title, String lastMessage) {
            this.chatRoomId = chatRoomId;
            this.usernameList = usernameList;
            this.title = title;
            this.lastMessage = lastMessage;
        }
    }
}
