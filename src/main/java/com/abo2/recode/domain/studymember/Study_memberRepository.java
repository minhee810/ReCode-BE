package com.abo2.recode.domain.studymember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface Study_memberRepository extends JpaRepository<Study_Member,Long> {

    List<Study_Member> findByUserId(Long userId);

    void deleteById(Long userId);

    // 스터디 탈퇴
    @Transactional
    @Modifying
    @Query("DELETE FROM Study_Member sm WHERE sm.user.id = :userId AND sm.studyRoom.id = :studyId")
    void deleteByUserIdAndStudyRoomUd(@Param("userId") Long userId, @Param("studyId") Long studyId);

     @Transactional
     @Modifying
     @Query(value = "DELETE FROM study_skill WHERE study_room_id = ?1", nativeQuery = true)
     void deleteByStudyId(Long studyId);
}
