package com.abo2.recode.dto.qna;

import com.abo2.recode.domain.qna.Qna;
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

}
