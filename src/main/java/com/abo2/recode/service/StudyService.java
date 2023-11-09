package com.abo2.recode.service;

import com.abo2.recode.domain.studyroom.StudyRepository;
import com.abo2.recode.domain.studyroom.StudyRoom;
import com.abo2.recode.dto.study.StudyReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudyService {

    @Autowired
    StudyRepository studyRepository;


    public void createRoom(StudyReqDto studyReqDto) {

        StudyRoom studyRoom = new StudyRoom();
        

        studyRepository.save(studyRoom);
    }

}


