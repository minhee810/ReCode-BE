package com.abo2.recode.domain.notice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Optional<Notice> findById(Long studyId);

    List<Notice> findAllByOrderByUpdatedAtDesc();

    List<Notice> findByTitleContaining(String title);

    @Query("SELECT n FROM Notice n WHERE n.createdBy.nickname LIKE %:createdBy%")
    List<Notice> findByCreatedByContaining(@Param("createdBy") String createdBy);


}

