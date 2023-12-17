package com.abo2.recode.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostReplyRepository extends JpaRepository<PostReply, Long> {

    List<PostReply> findByPostId(@Param("postId") Long postId);

    boolean existsByUserId(Long userId);
}
