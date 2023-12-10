package com.abo2.recode.dto.qna;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaResDTO {

    private Long qnaId;
    private Long userId;
    private String title;
    private String content;

    @Builder
    public QnaResDTO(Long qnaId, Long userId, String title, String content) {
        this.qnaId = qnaId;
        this.userId = userId;
        this.title = title;
        this.content = content;
    }
}
