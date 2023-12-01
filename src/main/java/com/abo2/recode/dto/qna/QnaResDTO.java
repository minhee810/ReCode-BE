package com.abo2.recode.dto.qna;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaResDTO {

    //private Long id;
    private Long user_id;
    private String title;
    private String category;
    private String content;

    @Builder
    public QnaResDTO(Long user_id, String title, String category, String content) {
        this.user_id = user_id;
        this.title = title;
        this.category = category;
        this.content = content;
    }
}
