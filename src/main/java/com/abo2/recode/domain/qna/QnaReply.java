package com.abo2.recode.domain.qna;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class QnaReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_reply_id")
    Long id;

    @Column(nullable = false)
    private String comment;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "qna_id", nullable = false, updatable = false)
    private Qna qnaId;

    @Builder
    public QnaReply(Long id, String comment, Qna qnaId) {
        this.id = id;
        this.comment = comment;
        this.qnaId = qnaId;

    }
}