package com.abo2.recode.domain.mentor;

import com.abo2.recode.domain.skill.Skill;
import com.abo2.recode.domain.studyroom.StudyRoom;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class MentorSkill {


    @Id
    @Column(name = "study_skill_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // MentorSkill 일련번호

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    @JsonIgnore
    private Mentor mentor;

    @OneToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @Builder
    public MentorSkill(Mentor mentor, Skill skill) {
        this.mentor = mentor;
        this.skill = skill;
    }
}
