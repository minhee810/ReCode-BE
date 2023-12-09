package com.abo2.recode.domain.studyroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM study_skill WHERE studyId = ?1", nativeQuery = true)
    void deleteByStudyId(Long studyId);
}
