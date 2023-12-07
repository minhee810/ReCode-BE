package com.abo2.recode.dto.chat;

import com.abo2.recode.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class ChatReqDto {

    @Getter
    @Setter
    public static class ChatCreateReqDto{

        @NotEmpty
        private String chatRoomTitle;

        @NotEmpty
        private User master;

        @NotEmpty
        private List<Long> userIdList;

        @Builder
        public ChatCreateReqDto(String chatRoomTitle, User master, List<Long> userIdList) {
            this.chatRoomTitle = chatRoomTitle;
            this.master = master;
            this.userIdList = userIdList;
        }
    }
}
