package com.abo2.recode.domain.skill;

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
    private Long id; //skill 일련번호

    @Column(unique = true,nullable = false,length = 50)
    private String skill_name; //skill 네임


}

//    Table Skill {
//        id integer [primary key]
//        skill_name varchar