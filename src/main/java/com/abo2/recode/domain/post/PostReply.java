package com.abo2.recode.domain.post;

import com.abo2.recode.domain.post.Post;
import com.abo2.recode.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PostReply {

//    Table Post_reply {
//        id integer [primary key]
//        content varchar
//        post_id integer
//        user_id integer
//        createdAt timestamp
//        updatedAt timestamp
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postReply_id")
    private Long Id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public PostReply(String content, Post post, User user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.content = content;
        this.post = post;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public PostReply(String content, Post post, User user) {
        this.content = content;
        this.post = post;
        this.user = user;
    }
}
