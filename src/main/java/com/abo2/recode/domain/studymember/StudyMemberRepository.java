package com.abo2.recode.domain.studymember;

import com.abo2.recode.dto.study.StudyResDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;


public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {

    List<StudyMember> findByUserId(Long userId);

    // 스터디 탈퇴
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Study_Member sm WHERE sm.user_id = :userId AND sm.studyId = :studyId", nativeQuery = true)
    void deleteByUserIdAndstudyId(@Param("userId") Long userId, @Param("studyId") Long studyId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM study_skill WHERE studyId =:studyId", nativeQuery = true)
    void deleteByStudyId(@Param(value = "studyId") Long studyId);

    // 사용자가 스터디 룸에 참여 중인지 여부 확인
    @Query(
            value = "SELECT * FROM Study_Member as sm WHERE sm.user_id=:userId AND sm.studyId=:studyId",
            nativeQuery = true
    )
    Optional<StudyMember> findByUserAndStudyRoom(@Param(value = "userId") Long userId,
                                                 @Param(value = "studyId") Long studyId);

    // 스터디룸 참가 인원 리스트 조회
    @Query(
            value = "SELECT * FROM Study_Member as sm WHERE sm.status = 1 AND sm.studyId=:studyId",
            nativeQuery = true
    )
    List<StudyMember> findBystudyId(@Param(value = "studyId") Long studyId);

    @Modifying
    @Query(value = "UPDATE study_member as sm SET sm.status =:status WHERE sm.studyId=:studyId" +
            " and sm.user_id=:userId", nativeQuery = true)
    void membershipUpdate(@Param(value = "status") Integer status,
                          @Param(value = "studyId") Long studyId,
                          @Param(value = "userId") Long userId);

    // 관리자의 스터디 그룹 관리 페이지에서 스터디 멤버 조회
    @Query(value = "SELECT * FROM study_member as sm WHERE studyId=:studyId AND status=1", nativeQuery = true)
    List<StudyMember> findApprovedMemberById(@Param(value = "studyId") Long studyId);

    @Query(
            name = "getApplicationResDto",
            nativeQuery = true
    )
    List<StudyResDto.ApplicationResDto> applications(@Param("studyId") Long studyId);


    @Query(
            name = "getApplicationEssayResDto",
            nativeQuery = true
    )
    StudyResDto.ApplicationEssayResDto applicationsEssay(@Param("studyId") Long studyId,
                                                         @Param("userId") Long userId);

    @Query(
            name = "StudyMemberAndStatusListRespDto",
            nativeQuery = true
    )
    List<StudyResDto.StudyMemberAndStatusListRespDto> getStudyMembersByRoomIdAsAdmin(@Param("studyId") Long studyId);
}
