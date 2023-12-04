package com.abo2.recode.dto.qna;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class QnaReplyDTO {

    private Long id;
    private Long qnaId;
    private String comment;
    private Long userId;
    
    

}
