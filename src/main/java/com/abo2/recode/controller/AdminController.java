package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.admin.AdminReqDto;
import com.abo2.recode.dto.admin.AdminResDto;
import com.abo2.recode.dto.skill.SkillReqDto;
import com.abo2.recode.dto.skill.SkillResDto;
import com.abo2.recode.handler.CustomExceptionHandler;
import com.abo2.recode.handler.ex.CustomForbiddenException;
import com.abo2.recode.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
    @Secured(value = "ROLE_ADMIN")
    @PostMapping(value = "/admin/v1/addskill")
    public ResponseEntity<ResponseDto> adminSkillAdd(
<<<<<<< HEAD
            @RequestBody SkillReqDto.AdminSkillAddReqDto adminSkillAddReqDto){
=======
            @RequestBody SkillReqDto.AdminSkillAddReqDto adminSkillAddReqDto,
            @AuthenticationPrincipal LoginUser loginUser
            ){
>>>>>>> 56341de84ae0310103060b69505743a8892c6fcd

        //유저가 관리자가 맞는지 검증
        if( !( loginUser.getUser().getRole().getValue().equals("관리자") )) {
            logger.error("관리자가 아님!!!!");
            return (ResponseEntity<ResponseDto>) new CustomExceptionHandler()
                    .forbiddenException(new CustomForbiddenException("관리자 아님!!"));
        }

        // 1. 쉼표로 분할하여 배열에 저장
        String[] addskills = adminSkillAddReqDto.getSkills().split(",");

        // 2. DB에 skill 테이블에 insert
        SkillResDto.AdminSkillAddResDto adminSkillAddResDto = adminService.adminSkillAdd(addskills);

        ResponseDto<SkillResDto.AdminSkillAddResDto> responseDto =
                new ResponseDto<>(1,"스킬 추가 성공",adminSkillAddResDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);

    } //adminSkillAdd()

    // 관리자의 스터디 모임 삭제 /api/v1/study/{study_id}
    @Secured(value = "ROLE_ADMIN")
    @DeleteMapping(value = "/admin/v1/study/{study_id}")
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

    // 관리자 스터디 그룹 일반 멤버 스터디 그룹 장으로 승급/강등
    @Secured(value = "ROLE_ADMIN")
    @PutMapping(value="/admin/v1/study-member/{study_id}/{user_id}")
    public ResponseEntity<ResponseDto> memberRoleChange(
            @RequestBody AdminReqDto.MemberRoleReqDto memberRoleReqDto,
            @AuthenticationPrincipal LoginUser loginUser
            ){

        //유저가 관리자가 맞는지 검증
        if( !( loginUser.getUser().getRole().getValue().equals("관리자") )) {
            logger.error("관리자가 아님!!!!");
            return (ResponseEntity<ResponseDto>) new CustomExceptionHandler()
                    .forbiddenException(new CustomForbiddenException("관리자 아님!!"));
        }

            /* MemberRoleReqDto
            {
            "user_id" : 1,
            "role": "group_leader" //or "group_member",
            "study_id" : 1
        }*/


        //    - 현재 그룹장이 자신의 권한을 이전할 의사가 있는지 확인하기 위한 추가적인 인증 절차가 필요할 수 있습니다.
        //-> 알람 기능 완성 시 고민하기

        //    - 한 번에 한 명의 멤버만이 그룹장이 될 수 있으므로, 권한 이전 시에 현재 그룹장은 자동으로 일반 멤버로 강등되는 로직이 필요합니다.
        AdminResDto.MemberRoleResDto memberRoleResDto = adminService.memberRoleChange(memberRoleReqDto);

        return new ResponseEntity<>(new ResponseDto<>(1,"사용자 권한이 성공적으로 변경되었습니다.",memberRoleResDto),
                HttpStatus.OK);
    }//memberRoleChange()


    // 관리자 권한으로 글 삭제


    // 관리자 기술 스택 불러오기 성공
    @Secured(value = "ROLE_ADMIN")
    @GetMapping(value = "/admin/v1/get-skills")
    public ResponseEntity<?> getSkills(){
        SkillResDto.AdminSkillAddResDto adminSkillAddResDto = adminService.getSkills();
        return new ResponseEntity<>(new ResponseDto<>(1, "스택 목록 불러오기 성공", adminSkillAddResDto), HttpStatus.OK);
    }

}//AdminController
