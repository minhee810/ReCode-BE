package com.abo2.recode.dto.post;

import com.abo2.recode.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostRespDto {

    private Long id;
    private String title;
    private String content;
    private Long studyRoomId;
    private Long userId;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Getter
    @Setter
    public static class PostListRespDto{
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
