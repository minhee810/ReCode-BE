package com.abo2.recode.dto.post;


import com.abo2.recode.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostReqDto {

    private String title;
    private String content;
    private String category;
    private Long studyRoomId;
    private Long userId;

    public static PostReqDto postReqDto(Post post) {
        return PostReqDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .studyRoomId(post.getStudyRoom().getId())
                .userId(post.getUser().getId())
                .build();
    }

}
