package com.abo2.recode.domain.quiz;

import com.abo2.recode.domain.studyroom.StudyRoom;
import com.abo2.recode.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Quiz {

//    Table Quiz{
//        quiz_id integer [primary key]
//        user_id integer
//        study_id integer
//        title varchar
//        quiz_link varchar
//        difficulty integer
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "study_room_id")
    private StudyRoom studyRoom;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(nullable = false)
    private String quiz_link;

    @Column(nullable = false)
    private Integer difficulty;

    @Builder
    public Quiz(Long id, String title, StudyRoom studyRoom, User user, String quiz_link, Integer difficulty) {
        this.id = id;
        this.title = title;
        this.studyRoom = studyRoom;
        this.user = user;
        this.quiz_link = quiz_link;
        this.difficulty = difficulty;
    }
}
