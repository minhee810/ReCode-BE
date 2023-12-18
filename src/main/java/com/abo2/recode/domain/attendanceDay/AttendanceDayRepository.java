package com.abo2.recode.domain.attendanceDay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface AttendanceDayRepository extends JpaRepository<AttendanceDay, Long> {
    Set<AttendanceDay> findByStudyRoomId(Long studyId);

    @Modifying
    @Query("DELETE FROM AttendanceDay ad WHERE ad.studyRoom.id = :studyId")
    void deleteAttendanceDaysByStudyRoomId(@Param("studyId") Long studyId);


}