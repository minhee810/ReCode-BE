package com.abo2.recode.service;

import com.abo2.recode.domain.StudyRoom.StudyRepository;
import com.abo2.recode.domain.StudyRoom.StudyRoom;
import com.abo2.recode.dto.study.StudyReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StudyService {

    @Autowired
    StudyRepository studyRepository;

    public void createRoom(StudyReqDto.StudyCreateReqDto studyCreateReqDto){

        //1. 넘겨 받은 studyReqDto에서 정보 가져오기

        String study_name = studyCreateReqDto.getStudy_name();
        String title = studyCreateReqDto.getTitle();
        String description = studyCreateReqDto.getDescription();
        LocalDateTime start_date = studyCreateReqDto.getStart_date();
        LocalDateTime end_date = studyCreateReqDto.getEnd_date();
        Integer max_num = studyCreateReqDto.getMax_num();
        String user_id = studyCreateReqDto.getUser_id();
        LocalDateTime createdAt = studyCreateReqDto.getCreatedAt();
        LocalDateTime updatedAt = studyCreateReqDto.getUpdatedAt();

        //2. DB에 전송할 Entity 선언, Entity에 데이터 집어 넣기
        StudyRoom studyRoom = StudyRoom.builder()
                .study_name(study_name)
                .title(title).description(description)
                .start_date(start_date)
                .end_date(end_date)
                .max_num(max_num)
                .created_By(user_id)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        studyRepository.save(studyRoom);
    }

}
