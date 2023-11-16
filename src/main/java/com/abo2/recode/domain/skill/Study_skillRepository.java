package com.abo2.recode.domain.skill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Study_skillRepository extends JpaRepository<Study_skill,Long> {

    List<Study_skill> findByStudyRoomId(Long studyRoomId);

}
