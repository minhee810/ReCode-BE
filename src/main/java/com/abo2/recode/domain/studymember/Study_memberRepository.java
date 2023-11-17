package com.abo2.recode.domain.studymember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface Study_memberRepository extends JpaRepository<Study_Member,Integer> {
     @Transactional
     @Modifying
     @Query(value = "DELETE FROM study_skill WHERE study_room_id = ?1", nativeQuery = true)
     void deleteByStudyId(Long studyId);
}
