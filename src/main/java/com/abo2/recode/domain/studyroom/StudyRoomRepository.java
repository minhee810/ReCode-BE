package com.abo2.recode.domain.studyroom;

import com.abo2.recode.domain.user.User;
import com.abo2.recode.dto.study.StudyReqDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StudyRoomRepository extends JpaRepository<StudyRoom,Long> {


    // userId,studyId를 기반으로 먼저 유저가 이미 가입했거나 가입한 상태인지 체크
    @Query(value = "SELECT study_member_id " +
            "FROM Study_Member WHERE study_room_id=:studyId AND user_id=:userId",nativeQuery = true)
    Long findIdByuserIdAndstudyId(@Param(value = "studyId")Long studyId,
                                    @Param(value = "userId")Long userId);

    @Query(value = "SELECT created_by FROM Study_Room s WHERE s.study_room_id=:studyId",nativeQuery = true)
    Long findcreatedByBystudyId(@Param("studyId") Long studyId);

    @Query("SELECT sr FROM StudyRoom sr LEFT JOIN FETCH sr.master LEFT JOIN FETCH sr.studySkills WHERE sr.id = :id")
    Optional<StudyRoom> findWithMasterAndSkillsById(@Param("id") Long id);

    @Query("SELECT sr FROM StudyRoom sr LEFT JOIN FETCH sr.master")
    List<StudyRoom> findAllWithMaster();

    // 사욪자가 스터디 장으로 있는지 확인
    @Query("SELECT COUNT(sr) > 0 FROM StudyRoom sr WHERE sr.master = :master")
    boolean existsByMaster(@Param("master") User master);

    @Modifying
    @Query(value = "UPDATE Study_Room s SET s.created_by = :userId WHERE s.study_room_id = :studyId",nativeQuery = true)
    void memberRolePromote(@Param("studyId") Long studyId, @Param("userId") Long userId);

    @Modifying
    @Query(value = "UPDATE Study_Room s SET s.created_by = null WHERE s.study_room_id = :studyId",nativeQuery = true)
    void memberRoleDemote(@Param("studyId") Long studyId);

 /*   @Modifying
    @Query(value = "UPDATE Study_Room s " +
            "SET s.study_name=:#{#dto.studyName}," +
            "s.title=:#{#dto.title}," +
            "s.description=:#{#dto.description}," +
            "s.startTime=:start_time," +
            "s.endTime=:end_time," +
            "s.startDate=:#{#dto.startDate}," +
            "s.endDate=:#{#dto.endDate}," +
            "s.maxNum=:#{#dto.maxNum}," +
            "s.createdAt=:#{#dto.createdAt}," +
            "s.updatedAt=:#{#dto.updatedAt} " +
            "WHERE s.studyId=:#{#dto.studyId} AND s.created_by=:#{#dto.userId};",
            nativeQuery = true)
    void modifyRoom(@Param("dto") StudyReqDto.StudyModifyReqDto dto
            ,@Param("start_time") LocalTime start_time
            ,@Param("end_time") LocalTime end_time);*/

}
