package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.study.StudyReqDto;
import com.abo2.recode.service.StudyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class StudyroomController {

    private static final Logger logger = LoggerFactory.getLogger(StudyroomController.class);


    @Autowired
    StudyService studyService;
    public StudyroomController(StudyService studyService) {
        this.studyService = studyService;
    }

    @Transactional
    @PostMapping(value="/v1/study") // @AuthenticationPrincipal 에서 LoginUser객체를 꺼내와야 함. LoginUSer
    public ResponseEntity<ResponseDto> createRoom(@AuthenticationPrincipal LoginUser loginUser, @RequestBody StudyReqDto.StudyCreateReqDto studyCreateReqDto){

        logger.info(loginUser.getUser().toString());
        // loginUser.getUser().getId() -> user id 담겨있음
        studyCreateReqDto.setUser_id(loginUser.getUser().getId());
        System.out.println("User : "+loginUser.getUser().getId());

        //1. studyReqDto를 DB에 넣기 Service에서 처리
        studyService.createRoom(studyCreateReqDto);

        ResponseDto<StudyReqDto.StudyCreateReqDto> responseDto
                = new ResponseDto<>(HttpStatus.OK.value(), "Success", studyCreateReqDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
