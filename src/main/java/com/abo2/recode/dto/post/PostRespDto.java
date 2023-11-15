package com.abo2.recode.dto.post;

import com.abo2.recode.domain.post.Post;
import lombok.Getter;
import lombok.Setter;

public class PostRespDto {

    @Getter
    @Setter
    public static class PostListRespDto{
        private Long id;
        private String title;
        private String content;
        private String category;

        public PostListRespDto(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.category = post.getContent();
        }
    }
}
