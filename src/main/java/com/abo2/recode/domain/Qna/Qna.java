package com.abo2.recode.domain.Qna;

import com.abo2.recode.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id")
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String content;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

//    @OneToOne
//    @JoinColumn(name = "user_id", nullable = false)
    private Long user_id;

    @Builder
    public Qna(Long user_id, String title, String category, String content, LocalDateTime createdAt,LocalDateTime updatedAt) {
        this.user_id = user_id;
        this.title = title;
        this.category = category;
        this.content = content;
        this.createdAt=createdAt;
        this.updatedAt = updatedAt;
    }
}
