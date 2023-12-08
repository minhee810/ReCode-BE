package com.abo2.recode.domain.attendanceDay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface AttendanceDayRepository extends JpaRepository<AttendanceDay, Long> {
    Set<AttendanceDay> findBystudyId(Long studyId);
}
