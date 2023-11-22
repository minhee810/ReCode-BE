package com.abo2.recode.dto.post;

import com.abo2.recode.domain.post.Post;
import lombok.AllArgsConstructor;
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
        private String category;
        private LocalDateTime createdAt;
        private String nickname;


        public PostListRespDto(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.category = getCategory(post.getCategory());
            this.createdAt = post.getCreatedAt();
            this.nickname = post.getUser().getNickname();
        }

        private String getCategory(Integer category) {
            switch (category) {
                case 0:
                    return "공지사항";
                case 1:
                    return "회고록";
                case 2:
                    return "자료 공유";
                default:
                    return "전체보기";
            }
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
