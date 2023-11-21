package com.abo2.recode.domain.studymember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudyMemberRepository extends JpaRepository<StudyMember,Long> {

    List<StudyMember> findByUserId(Long userId);

    // 스터디 탈퇴
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM StudyMember sm WHERE sm.user.id = :userId AND sm.studyRoom.id = :studyId", nativeQuery = true)
    void deleteByUserIdAndStudyRoomUd(@Param("userId") Long userId, @Param("studyId") Long studyId);

     @Transactional
     @Modifying
     @Query(value = "DELETE FROM study_skill WHERE study_room_id = ?1", nativeQuery = true)
     void deleteByStudyId(Long studyId);
}
