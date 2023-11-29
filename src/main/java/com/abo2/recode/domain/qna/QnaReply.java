package com.abo2.recode.domain.qna;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class QnaReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_reply_id")
    Long id;


    @Column(nullable = false)
    private String comment;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "qna_id", nullable = false)
    private Qna qna_id;



    @Builder
    public QnaReply(Long id, String comment, LocalDateTime createdAt, Qna qna_id) {
        this.id = id;
        this.comment = comment;
        this.createdAt = createdAt;
        this.qna_id = qna_id;

    }
}
