package com.abo2.recode.domain.studyroom;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyRoomRepository extends JpaRepository<StudyRoom,Long> {

    Optional<StudyRoom> findById(Long study_room_id);

}
