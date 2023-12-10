package com.abo2.recode.dto.qna;

import com.abo2.recode.domain.qna.QnaReply;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
public class QnaReplyDTO {

    private Long id;
    private Long qnaId;
    private String comment;
    private Long userId;


}
