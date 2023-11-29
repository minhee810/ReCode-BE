package com.abo2.recode.domain.notice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Optional<Notice> findById(Long studyId);

    List<Notice> findAll();
}
