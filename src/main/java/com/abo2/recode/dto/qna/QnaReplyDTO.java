package com.abo2.recode.dto.qna;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class QnaReplyDTO {

    private Long id;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long qnaId;
    private Long userId;
    private String nickname;

    @Builder
    public QnaReplyDTO(Long id, String comment, LocalDateTime createdAt, LocalDateTime updatedAt, Long qnaId, Long userId, String nickname) {

        this.id = id;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.qnaId = qnaId;
        this.userId = userId;
        this.nickname = nickname;
    }
}
