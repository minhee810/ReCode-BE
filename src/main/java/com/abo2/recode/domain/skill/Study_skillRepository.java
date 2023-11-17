package com.abo2.recode.domain.skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
public interface Study_skillRepository extends JpaRepository<Study_skill,Long> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM study_skill WHERE study_room_id = ?1", nativeQuery = true)
    void deleteByStudyId(Long studyId);

}
