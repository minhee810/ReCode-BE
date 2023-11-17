package com.abo2.recode.domain.quiz;

import com.abo2.recode.domain.studyroom.StudyRoom;
import com.abo2.recode.domain.user.User;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "study_room_id")
    private StudyRoom studyRoom;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(nullable = false)
    private String quiz_link;

    @Column(nullable = false)
    private Integer difficulty;
}
