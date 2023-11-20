package com.abo2.recode.dto.post;

import com.abo2.recode.domain.post.Post;
import com.abo2.recode.domain.studyroom.StudyRoom;
import com.abo2.recode.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


public class PostRespDto {


    private Long id;
    private String title;
    private String content;
    private StudyRoom studyRoomId;
    private User userId;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Getter
    @Setter
    public static class PostWriteRespDto {
        private Long id;
        private String title;
        private String content;
        private StudyRoom studyRoomId;
        private User userId;
        private String category;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public PostWriteRespDto(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.studyRoomId = post.getStudyRoom();
            this.userId = post.getUser();
            this.category = post.getCategory();
            this.createdAt = post.getCreatedAt();
            this.updatedAt = post.getUpdatedAt();
        }
    }

    @Getter
    @Setter
    public static class PostListRespDto {
        private Long id;
        private String title;
        private String content;
        private String category;
        private LocalDateTime createdAt;
        private String nickname;

        public PostListRespDto(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.category = post.getCategory();
            this.createdAt = post.getCreatedAt();
            this.nickname = post.getUser().getNickname();
        }
    }


}
