package com.abo2.recode.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostReplyRepository extends JpaRepository<PostReply, Long> {

    List<PostReply> findByPostId(Long postId);
}
