package com.abo2.recode.domain.skill;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill,Long> {

    //skillName을 기준으로 skill을 조회하는 쿼리 메소드
    Skill findBySkillNames(String skillnames);

}
