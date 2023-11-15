package com.abo2.recode.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.studyRoom.Id = :studyRoomId")
    List<Post> findPostsByStudyRoomId(@Param("studyRoomId") Long studyRoomId);
}
