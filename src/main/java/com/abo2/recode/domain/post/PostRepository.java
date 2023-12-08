package com.abo2.recode.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 게시글 불러오기
    @Query("SELECT p FROM Post p WHERE p.studyRoom.id = :studyId")
    List<Post> findPostsByStudyId(@Param("studyId") Long studyId);

    // 게시글 검색
    @Query("SELECT p FROM Post p WHERE p.studyRoom.id = :studyId AND (p.title LIKE %:keyword% OR p.user.nickname LIKE %:keyword% OR CAST(p.category AS string) LIKE %:keyword%)")
    List<Post> findPostsByKeyword(@Param("studyId") Long studyId, @Param("keyword") String keyword);

    @Query("SELECT p FROM Post p WHERE p.studyRoom.id = :studyId AND p.category = :category")
    List<Post> findPostsByCategory(@Param("studyId") Long studyId, @Param("category") Integer category);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM study_skill WHERE studyId = ?1", nativeQuery = true)
    void deleteByStudyId(Long studyId);
}
