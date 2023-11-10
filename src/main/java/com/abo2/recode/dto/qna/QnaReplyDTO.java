package com.abo2.recode.dto.qna;

import java.time.LocalDateTime;

public class QnaReplyDTO {

    private Long id;
    private Long qna_id;

    private String comment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
