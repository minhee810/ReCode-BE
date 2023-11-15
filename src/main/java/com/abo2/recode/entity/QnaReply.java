package com.abo2.recode.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "qna_reply")
public class QnaReply {

    @Id
    private Long id;

    private Long qna_id;

    private String comment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
