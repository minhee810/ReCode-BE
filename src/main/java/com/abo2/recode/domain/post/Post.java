package com.abo2.recode.domain.post;


import com.abo2.recode.domain.studyroom.StudyRoom;
import com.abo2.recode.domain.user.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Post 일련번호 - PK

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 300)
    private String content;

    @ManyToOne
    @JoinColumn(name = "study_room_id")
    private StudyRoom studyRoom;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private Integer category;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostReply> postReplies;


    @Builder
    public Post(String title, String content, StudyRoom studyRoom, User user, Integer category) {
        this.title = title;
        this.content = content;
        this.studyRoom = studyRoom;
        this.user = user;
        this.category = category;
    }

    public void PostWrite(String title, String content, StudyRoom studyRoom, User user, Integer category) {
        this.title = title;
        this.content = content;
        this.studyRoom = studyRoom;
        this.user = user;
        this.category = category;
    }


}
