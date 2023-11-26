package com.abo2.recode.domain.studymember;

import com.abo2.recode.dto.study.StudyResDto;
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

    @Modifying
    @Query(value = "UPDATE study_member as sm SET sm.status =:status WHERE sm.study_room_id=:studyId" +
            " and sm.user_id=:userId",nativeQuery = true)
    void membershipUpdate(@Param(value = "status") Integer status,
                          @Param(value = "studyId")Long studyId,
                          @Param(value = "userId")Long userId);

    // 관리자의 스터디 그룹 관리 페이지에서 스터디 멤버 조회
    @Query(value = "SELECT * FROM study_member as sm WHERE study_id=:studyId AND status=1",nativeQuery = true)
    List<StudyMember> findApprovedMemberById(@Param(value = "studyId") Long studyId);

    @Query(value="SELECT sm.user_id,u.username,sm.status,u.email " +
            "FROM Study_Member as sm " +
            "LEFT OUTER JOIN Users as u ON sm.user_id = u.user_id " +
            "WHERE sm.study_room_id=:study_room_id AND sm.status = 0"
            ,nativeQuery = true)
    List<Object> applications(@Param("study_room_id") Long study_room_id);
}
