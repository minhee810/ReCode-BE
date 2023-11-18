package com.abo2.recode.domain.studyroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudyRoomRepository extends JpaRepository<StudyRoom,Long> {

    Optional<StudyRoom> findById(Long Id);

    @Query("SELECT sr FROM StudyRoom sr LEFT JOIN FETCH sr.master")
    List<StudyRoom> findAllWithMaster();
}
