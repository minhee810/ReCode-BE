package com.abo2.recode.dto.post;

import com.abo2.recode.domain.post.Post;
import com.abo2.recode.domain.studyroom.StudyRoom;
import com.abo2.recode.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
public class PostRespDto {

    private Long id;
    private String title;
    private String content;
    private Integer category;
    private LocalDateTime createdAt;
    private String nickname;


    @Getter
    @Setter
    public static class PostWriteRespDto {
        private Long id;
        private String title;
        private String content;
        private Integer category;
        private LocalDateTime createdAt;
        private String nickname;

        public PostWriteRespDto(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.category = post.getCategory();
        }

    }

    @Getter
    @Setter
    public static class PostListRespDto {
        private Long id;
        private String title;
        private String content;
        private Integer category;
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

    @Getter
    @Setter
    public static class PostDetailRespDto {
        private Long id;
        private String title;
        private String content;
        private Integer category;
        private LocalDateTime createdAt;
        private String nickname;

        public PostDetailRespDto(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.category = post.getCategory();
            this.createdAt = post.getCreatedAt();
            this.nickname = post.getUser().getNickname();
        }
    }


}
