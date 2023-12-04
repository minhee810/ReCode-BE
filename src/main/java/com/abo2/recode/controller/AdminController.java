package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.admin.AdminReqDto;
import com.abo2.recode.dto.admin.AdminResDto;
import com.abo2.recode.dto.skill.SkillReqDto;
import com.abo2.recode.dto.skill.SkillResDto;
import com.abo2.recode.dto.study.StudyResDto;
import com.abo2.recode.handler.CustomExceptionHandler;
import com.abo2.recode.handler.ex.CustomForbiddenException;
import com.abo2.recode.service.AdminService;
import com.abo2.recode.service.StudyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(StudyroomController.class);

    private AdminService adminService;

    private StudyService studyService;

    @Autowired
    public AdminController(AdminService adminService, StudyService studyService) {
        this.adminService = adminService;
        this.studyService = studyService;
    }

    //관리자가 기술 스택 추가
    @Secured(value = "ROLE_ADMIN")
    @PostMapping(value = "/admin/v1/addskill")
    public ResponseEntity<ResponseDto> adminSkillAdd(
            @RequestBody SkillReqDto.AdminSkillAddReqDto adminSkillAddReqDto,
            @AuthenticationPrincipal LoginUser loginUser
    ) {

        //유저가 관리자가 맞는지 검증
        if (!(loginUser.getUser().getRole().getValue().equals("관리자"))) {
            logger.error("관리자가 아님!!!!");
            return (ResponseEntity<ResponseDto>) new CustomExceptionHandler()
                    .forbiddenException(new CustomForbiddenException("관리자 아님!!"));
        }

        // 1. 쉼표로 분할하여 배열에 저장
        String[] addskills = adminSkillAddReqDto.getSkills().split(",");

        // 2. DB에 skill 테이블에 insert
        SkillResDto.AdminSkillAddResDto adminSkillAddResDto = adminService.adminSkillAdd(addskills);

        ResponseDto<SkillResDto.AdminSkillAddResDto> responseDto =
                new ResponseDto<>(1, "스킬 추가 성공", adminSkillAddResDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);

    }

    @Secured(value = "ROLE_ADMIN")
    @DeleteMapping(value = "/admin/v1/study/{studyId}")
    public ResponseEntity<ResponseDto> adminStudyRoomDelete(
            @PathVariable Long studyId,
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        // Debugging: Log the user information
        logger.info("Logged-in user: {}", loginUser);

        //유저가 관리자가 맞는지 검증
        if (!(loginUser.getUser().getRole().getValue().equals("관리자"))) {
            logger.error("관리자가 아님!!!!");
            return (ResponseEntity<ResponseDto>) new CustomExceptionHandler()
                    .forbiddenException(new CustomForbiddenException("관리자 아님!!"));
        }

        // DB에서 StudyRoom Entity 삭제
        AdminResDto.StudyDeleteResponseDto studyDeleteResponseDto
                = adminService.adminStudyRoomDelete(studyId);

        ResponseDto<AdminResDto.StudyDeleteResponseDto> responseDto
                = new ResponseDto<>(1, "스터디 모집 글이 성공적으로 삭제되었습니다", studyDeleteResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 관리자 스터디 그룹 일반 멤버 스터디 그룹 장으로 승급/강등
    @Secured(value = "ROLE_ADMIN")
    @PutMapping(value = "/admin/v1/study-member/{studyId}/{userId}")
    public ResponseEntity<ResponseDto> memberRoleChange(
            @RequestBody AdminReqDto.MemberRoleReqDto memberRoleReqDto,
            @PathVariable(name = "studyId") Long studyId,
            @PathVariable(name = "userId") Long userId
    ) {

        //    - 한 번에 한 명의 멤버만이 그룹장이 될 수 있으므로, 권한 이전 시에 현재 그룹장은 자동으로 일반 멤버로 강등되는 로직이 필요합니다.
        AdminResDto.MemberRoleResDto memberRoleResDto
                = adminService.memberRoleChange(memberRoleReqDto, studyId, userId);

        return new ResponseEntity<>(new ResponseDto<>(1, "사용자 권한이 성공적으로 변경되었습니다.", memberRoleResDto),
                HttpStatus.OK);
    }

    // 스터디 그룹에서 멤버 목록 불러오기 (study_member의 역할까지 불러와야함.
    @GetMapping(value = "/v1/study/{studyId}/memberlistandstatus")
    public ResponseEntity<?> getsStudyMembersandStatus(@PathVariable("studyId") Long studyRoomId) {

        List<StudyResDto.StudyMemberAndStatusListRespDto> studyMembers = studyService.getStudyMembersByRoomIdAsAdmin(studyRoomId);

        if (studyMembers.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(new ResponseDto<>
                    (1, "스터디 그룹 멤버 목록입니다.", studyMembers), HttpStatus.OK);
        }

    }

    // 관리자 기술 스택 불러오기 성공
    @GetMapping(value = "/get-skills")
    public ResponseEntity<?> getSkills() {
        SkillResDto.AdminSkillAddResDto adminSkillAddResDto = adminService.getSkills();
        return new ResponseEntity<>(new ResponseDto<>(1, "스택 목록 불러오기 성공", adminSkillAddResDto), HttpStatus.OK);
    }

}