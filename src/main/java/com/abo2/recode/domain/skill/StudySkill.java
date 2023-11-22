package com.abo2.recode.domain.skill;

import com.abo2.recode.domain.studyroom.StudyRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class StudySkill {

    @Id
    @Column(name = "study_skill_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //스터디 그룹 스킬 일련번호

    @ManyToOne
    @JoinColumn(name = "study_room_id")
    private StudyRoom studyRoom;

    @OneToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @Builder
    public StudySkill(StudyRoom studyRoom, Skill skill) {
        this.studyRoom = studyRoom;
        this.skill = skill;
    }
}


  /*  Table Study_skill {
        id integer [primary key]
        study_id integer
        skill_id integer
        }

        }*/
