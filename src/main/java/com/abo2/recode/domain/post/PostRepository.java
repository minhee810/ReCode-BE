package com.abo2.recode.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 게시글 불러오기
    @Query("SELECT p FROM Post p WHERE p.studyRoom.id = :studyRoomId")
    List<Post> findPostsByStudyRoomId(@Param("studyRoomId") Long studyRoomId);

    // 게시글 검색
    @Query("SELECT p FROM Post p WHERE p.studyRoom.id = :studyRoomId AND (p.title LIKE %:keyword% OR p.user.nickname LIKE %:keyword% OR p.category LIKE %:keyword%)")
    List<Post> findPostsByKeyword(@Param("studyRoomId") Long studyRoomId, @Param("keyword") String keyword);
}
