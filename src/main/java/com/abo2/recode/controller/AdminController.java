package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.admin.AdminResDto;
import com.abo2.recode.dto.skill.SkillReqDto;
import com.abo2.recode.dto.skill.SkillResDto;
import com.abo2.recode.handler.CustomExceptionHandler;
import com.abo2.recode.handler.ex.CustomForbiddenException;
import com.abo2.recode.service.AdminService;
import com.abo2.recode.service.StudyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    // 관리자의 스터디 모임 삭제 /api/v1/study/{study_id}
    @DeleteMapping(value = "/v1/study/{study_id}")
    public ResponseEntity<ResponseDto> adminStudyRoomDelete(
            @PathVariable Long study_id,
            @AuthenticationPrincipal LoginUser loginUser
            ){

        // Debugging: Log the user information
        logger.info("Logged-in user: {}", loginUser);

        //유저가 관리자가 맞는지 검증
        if( !( loginUser.getUser().getRole().getValue().equals("관리자") )) {
            logger.error("관리자가 아님!!!!");
            return (ResponseEntity<ResponseDto>) new CustomExceptionHandler()
                    .forbiddenException(new CustomForbiddenException("관리자 아님!!"));
        }

            // DB에서 StudyRoom Entity 삭제
            AdminResDto.StudyDeleteResponseDto studyDeleteResponseDto
                    = adminService.adminStudyRoomDelete(study_id);

            ResponseDto<AdminResDto.StudyDeleteResponseDto> responseDto
                    = new ResponseDto<>(1,"스터디 모집 글이 성공적으로 삭제되었습니다",studyDeleteResponseDto);

            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }//adminStudyRoomDelete()

    @GetMapping(value = "/v1/get-skills")
    public ResponseEntity<?> getSkills(){
        SkillResDto.AdminSkillAddResDto adminSkillAddResDto = adminService.getSkills();
        return new ResponseEntity<>(new ResponseDto<>(1, "스택 목록 불러오기 성공", adminSkillAddResDto), HttpStatus.OK);
    }


}//AdminController
