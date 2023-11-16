package com.abo2.recode.dto.post;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class PostReplyRespDto {

    private Long id;
    private String content;
    private Long postId;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
