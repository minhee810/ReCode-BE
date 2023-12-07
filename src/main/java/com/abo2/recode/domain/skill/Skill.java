package com.abo2.recode.domain.skill;

import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long id; //skill 일련번호


    @Column(unique = true, nullable = false, length = 50)  //
    private String skillName; // skill 네임


    @Column(nullable = false, length = 50)
    private String position;

    @Builder
    public Skill(String skillName, String position) {
        this.skillName = skillName;
        this.position = position;
    }


//    public void completeSignUp() {
//        this.email = email;
//        this.createdAt = createdAt;
//    }

}

