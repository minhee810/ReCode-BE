package com.abo2.recode.dto.post;

import com.abo2.recode.domain.post.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostDetailRespDto {

    private Long id;
    private String title;
    private String content;
    private Long studyRoomId;
    private Long userId;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String nickname;


    public PostDetailRespDto(Long id, String title, String content, Long studyRoomId, Long userId, String category, LocalDateTime createdAt, LocalDateTime updatedAt, String nickname) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.studyRoomId = studyRoomId;
        this.userId = userId;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.nickname = nickname;
    }

    public PostDetailRespDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.studyRoomId = post.getStudyRoom().getId();
        this.userId = post.getUser().getId();
        this.category = post.getCategory();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.nickname = post.getUser().getNickname();
    }

}
