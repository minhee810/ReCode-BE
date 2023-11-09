package com.abo2.recode.controller;

import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.study.StudyReqDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class StudyroomController {

    @PostMapping(value="/v1/study")
    public ResponseEntity<ResponseDto> createRoom(@RequestBody StudyReqDto studyReqDto){

        ResponseDto<StudyReqDto> responseDto = new ResponseDto<>(HttpStatus.OK.value(), "Success", studyReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
