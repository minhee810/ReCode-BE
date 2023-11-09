package com.abo2.recode.domain.studyroom;


import com.abo2.recode.service.StudyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudyRepository extends JpaRepository<StudyRoom, Long> {

    public void createRoom(StudyRoom studyRoom);
}
