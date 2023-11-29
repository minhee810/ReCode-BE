package com.abo2.recode.domain.studyroom;

import com.abo2.recode.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudyRoomRepository extends JpaRepository<StudyRoom,Long> {

    Optional<StudyRoom> findById(Long study_room_id);

    // user_id,study_id를 기반으로 먼저 유저가 이미 가입했거나 가입한 상태인지 체크
    @Query(value = "SELECT study_member_id " +
            "FROM Study_Member WHERE study_room_id=:study_room_id AND user_id=:user_id",nativeQuery = true)
    Long findIdByuser_idAndStudy_id(@Param(value = "study_room_id")Long study_room_id,
                                    @Param(value = "user_id")Long user_id);

    @Query(value = "SELECT created_by FROM Study_Room s WHERE s.study_room_id=:study_room_id",nativeQuery = true)
    Long findCreated_byBystudy_id(@Param("study_room_id") Long study_room_id);

    @Query("SELECT sr FROM StudyRoom sr LEFT JOIN FETCH sr.master LEFT JOIN FETCH sr.studySkills WHERE sr.id = :id")
    Optional<StudyRoom> findWithMasterAndSkillsById(@Param("id") Long id);

    @Query("SELECT sr FROM StudyRoom sr LEFT JOIN FETCH sr.master")
    List<StudyRoom> findAllWithMaster();

    // 사욪자가 스터디 장으로 있는지 확인
    @Query("SELECT COUNT(sr) > 0 FROM StudyRoom sr WHERE sr.master = :master")
    boolean existsByMaster(@Param("master") User master);

    @Modifying
    @Query(value = "UPDATE Study_Room s SET s.created_by = :user_id WHERE s.study_room_id = :study_room_id",nativeQuery = true)
    void memberRolePromote(@Param("study_room_id") Long study_room_id, @Param("user_id") Long user_id);

    @Modifying
    @Query(value = "UPDATE Study_Room s SET s.created_by = null WHERE s.study_room_id = :study_room_id",nativeQuery = true)
    void memberRoleDemote(@Param("study_room_id") Long study_room_id);

}
