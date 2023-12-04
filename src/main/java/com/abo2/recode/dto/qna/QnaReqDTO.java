package com.abo2.recode.dto.qna;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QnaReqDTO {

    private Long userId;
    private String title;
    private String category;
    private String content;

}
