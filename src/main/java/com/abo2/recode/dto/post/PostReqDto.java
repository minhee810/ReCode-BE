package com.abo2.recode.dto.post;


import com.abo2.recode.domain.post.Post;
import com.abo2.recode.domain.studyroom.StudyRoom;
import com.abo2.recode.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

public class PostReqDto {

    @Getter
    @Setter
    public static class PostWriteReqDto {

        @NotEmpty
        private String title;

        @NotEmpty
        private String content;

        @NotEmpty
        private Long studyRoomId;

        @NotEmpty
        private Long userId;

        @NotEmpty
        private Integer category;


    }





}

