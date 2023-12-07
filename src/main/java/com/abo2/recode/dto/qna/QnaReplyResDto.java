package com.abo2.recode.dto.qna;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
public class QnaReplyResDto {

    @NotEmpty
    Long id;

    @NotEmpty
    private String comment;

    @NotEmpty
    private LocalDateTime createdAt;

    @NotEmpty
    private LocalDateTime updatedAt;

    @Builder
    public QnaReplyResDto(Long id, String comment, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
