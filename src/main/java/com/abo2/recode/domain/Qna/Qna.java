package com.abo2.recode.domain.Qna;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "qna")
public class Qna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long qna_id;


    private Long user_id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String content;

//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;

    @Builder

    public Qna(Long qna_id, Long user_id, String title, String category, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {

        this.qna_id = qna_id;
        this.user_id = user_id;
        this.title = title;
        this.category = category;
        this.content = content;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
    }
}
