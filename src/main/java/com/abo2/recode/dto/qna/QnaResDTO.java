package com.abo2.recode.dto.qna;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaResDTO {

    private Long userId;
    private String title;
    private String category;
    private String content;

    @Builder
    public QnaResDTO(Long userId, String title, String category, String content) {
        this.userId = userId;
        this.title = title;
        this.category = category;
        this.content = content;
    }
}
