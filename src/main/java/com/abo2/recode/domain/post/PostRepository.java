package com.abo2.recode.domain.post;

import com.abo2.recode.domain.studyroom.StudyRoom;
import com.abo2.recode.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.studyRoom.Id = :studyRoomId")
    List<Post> findPostsByStudyRoomId(@Param("studyRoomId") Long studyRoomId);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM study_skill WHERE study_room_id = ?1", nativeQuery = true)
    void deleteByStudyId(Long studyId);
}
