package com.abo2.recode.domain.attendanceDay;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface AttendanceDayRepository extends JpaRepository<AttendanceDay, Long> {
    Set<AttendanceDay> findByStudyRoomId(Long studyId);
}