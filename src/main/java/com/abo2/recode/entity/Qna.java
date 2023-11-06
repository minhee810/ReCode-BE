package com.abo2.recode.entity;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "qna")
public class Qna {
    @Id
    private Long qna_id;


    private Long user_id;
    private String title;
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
