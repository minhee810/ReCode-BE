package com.abo2.recode.domain.skill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;


public interface Study_skillRepository extends JpaRepository<Study_skill,Long> {

    List<Study_skill> findByStudyRoomId(Long studyRoomId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM study_skill WHERE study_room_id = ?1", nativeQuery = true)
    void deleteByStudyId(Long studyId);

}
