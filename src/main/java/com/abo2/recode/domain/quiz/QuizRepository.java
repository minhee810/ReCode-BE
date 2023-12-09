package com.abo2.recode.domain.quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM study_skill WHERE studyId = ?1", nativeQuery = true)
    void deleteByStudyId(Long studyId);

    @Query("SELECT q FROM Quiz q WHERE q.studyRoom.id = :studyId")
    List<Quiz> findQuizBystudyId(@Param("studyId") Long studyId);

    @Query("SELECT q FROM Quiz q WHERE q.studyRoom.id = :studyId AND (CAST(q.id AS string) LIKE %:keyword% OR q.title LIKE %:keyword% OR CAST(q.difficulty AS string) LIKE %:keyword% OR q.user.nickname LIKE %:keyword% )")
    List<Quiz> findQuizByKeyword(@Param("studyId") Long studyId, @Param("keyword") String keyword);
}
