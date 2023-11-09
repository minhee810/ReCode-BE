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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studySkill_id")
    private Long id; //스터디 그룹 스킬 일련번호

    @ManyToOne
    @JoinColumn(name = "studyRoom_id")
    private StudyRoom studyRoom;

    @OneToOne
    @JoinColumn(name = "skill_id")
    private Skill skills;

    @Builder
    public StudySkill(StudyRoom studyRoom, Skill skills) {
        this.studyRoom = studyRoom;
        this.skills = skills;
    }
}

