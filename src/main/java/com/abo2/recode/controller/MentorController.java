package com.abo2.recode.controller;

import com.abo2.recode.domain.mentor.Mentor;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class MentorController {

    MentorService mentorService;

    @Autowired
    public MentorController(MentorService mentorService) {
        this.mentorService = mentorService;
    }

    @GetMapping(value = "/v1/study/{studyRoomId}/get-recommend")
    public ResponseEntity<?> getMentorRecommend(
            @PathVariable(name = "studyRoomId") Long studyRoomId
    ){
        List<Mentor> mentorList = mentorService.getMentorRecommend(studyRoomId);
        return new ResponseEntity<>(new ResponseDto<>(1,"추천받은 멘토 리스트입니다.",mentorList), HttpStatus.OK);
    }
}
