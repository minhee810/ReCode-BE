package com.abo2.recode.domain.skill;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill,Long> {

    //skillName을 기준으로 skill을 조회하는 쿼리 메소드
    Skill findBySkillName(String skillname);
    Skill findBySkillNameAndPosition(String skillName, String position);

    List<Skill> findByPosition(String position);

}
