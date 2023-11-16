package com.abo2.recode.controller;

import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.skill.SkillReqDto;
import com.abo2.recode.dto.skill.SkillResDto;
import com.abo2.recode.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(StudyroomController.class);

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    //관리자가 기술 스택 추가
    @PostMapping(value = "/v1/admin/addskill")
    public ResponseEntity<ResponseDto> adminSkillAdd(
            @RequestBody SkillReqDto.AdminSkillAddReqDto adminSkillAddReqDto
            ){

        // 1. 쉼표로 분할하여 배열에 저장
        String[] addskills = adminSkillAddReqDto.getSkills().split(",");

        // 2. DB에 skill 테이블에 insert
        SkillResDto.AdminSkillAddResDto adminSkillAddResDto = adminService.adminSkillAdd(addskills);

        ResponseDto<SkillResDto.AdminSkillAddResDto> responseDto =
                new ResponseDto<>(1,"스킬 추가 성공",adminSkillAddResDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);

    } //adminSkillAdd()

}//AdminController
