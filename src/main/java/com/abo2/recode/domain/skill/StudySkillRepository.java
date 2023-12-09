package com.abo2.recode.domain.skill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;


public interface StudySkillRepository extends JpaRepository<StudySkill, Long> {

    List<StudySkill> findBystudyId(Long studyId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM study_skill WHERE studyId = ?1", nativeQuery = true)
    void deleteByStudyId(Long studyId);

}
