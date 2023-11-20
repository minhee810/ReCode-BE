package com.abo2.recode.domain.qna;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class QnaReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_reply")
    private Long id;

    @Column(nullable = false)
    private String comment;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "qna_id", nullable = false)
    private Qna qna_id;





}
