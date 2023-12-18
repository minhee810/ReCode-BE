package com.abo2.recode.domain.attendance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM study_skill WHERE study_room_id = ?1", nativeQuery = true)
    void deleteByStudyId(Long studyId);


    // 출석체크 중복방지
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END " +
            "FROM Attendance " +
            "WHERE study_room_id = :studyRoomId " +
            "AND user_id = :userId ")
    boolean existsByStudyRoomIdAndUserId(
            @Param("studyRoomId") Long studyRoomId,
            @Param("userId") Long userId);

    // 출석체크
    @Modifying
    @Query("UPDATE Attendance a SET a.status = :status WHERE a.studyRoom.id = :studyId and a.user.id = :userId")
    void markAttendance(Integer status, Long studyId, Long userId);

}