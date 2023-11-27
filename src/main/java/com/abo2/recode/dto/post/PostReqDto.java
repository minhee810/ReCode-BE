package com.abo2.recode.dto.post;


import com.abo2.recode.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

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

        @NotEmpty
        private User nickname;


    }


    @Getter
    @Setter
    public static class PostDetailReqDto {

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

        private User nickname;

    }

    @Getter
    @Setter
    public static class PostUpdateReqDto {

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

    @Getter
    @Setter
    public static class PostReplyReqDto {

        @NotEmpty
        private Long postId;

        @NotEmpty
        private Long postReplyId;

        @NotEmpty
        private Long userId;

        @NotEmpty
        private String content;

        @NotEmpty
        private User nickName;

        @CreatedDate
        private LocalDateTime createdAt;
    }


}

