package com.abo2.recode.domain.studyroom;

import com.abo2.recode.domain.skill.Skill;
import com.abo2.recode.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Getter
@Entity
public class StudyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "studyRoom_id")
    private StudyRoom studyRoom;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private int status;


}
