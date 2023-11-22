package com.abo2.recode.domain.studymember;

import com.abo2.recode.domain.studyroom.StudyRoom;
import com.abo2.recode.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

     // 사용자가 스터디 룸에 참여 중인지 여부 확인
     Optional<StudyMember> findByUserAndStudyRoom(User user, StudyRoom studyRoom);
}
