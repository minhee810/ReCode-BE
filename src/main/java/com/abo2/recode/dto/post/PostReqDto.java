package com.abo2.recode.dto.post;


import com.abo2.recode.domain.studyroom.StudyRoom;
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


        private Long postId;
        private String title;
        private String content;
        private Long studyRoomId;
        private Long userId;
        private Integer category;
        private User nickname;
        private String fileName;

    }


    @Getter
    @Setter
    public static class PostDetailReqDto {

        @NotEmpty
        private String title;

        @NotEmpty
        private String content;

        @NotEmpty
        private Long studyId;

        @NotEmpty
        private Long userId;

        @NotEmpty
        private Integer category;

        private User nickName;

    }

    @Getter
    @Setter
    public static class PostUpdateReqDto {

        @NotEmpty
        private String title;

        @NotEmpty
        private String content;

        @NotEmpty
        private Long studyId;

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

        private Long studyId;
    }


}

