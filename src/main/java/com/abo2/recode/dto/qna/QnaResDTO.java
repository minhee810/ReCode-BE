package com.abo2.recode.dto.qna;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class QnaResDTO {
    private Long id;
    private Long user_id;
    private String title;
    private String category;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
