package com.abo2.recode.domain.qna;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaReplyRepository extends JpaRepository<QnaReply, Long> {
    List<QnaReply> findByQnaId(Long id);

    boolean existsByUserId(Long userId);
}
