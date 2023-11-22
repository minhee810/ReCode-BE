package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.study.StudyReqDto;
import com.abo2.recode.dto.study.StudyResDto;
import com.abo2.recode.service.StudyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api")
public class StudyMemberController {

    private static final Logger logger = LoggerFactory.getLogger(StudyroomController.class);

    private StudyService studyService;

    @Autowired
    public StudyMemberController(StudyService studyService) {
        this.studyService = studyService;
    }

    //스터디 조장의 스터디 조원 승인
    @PostMapping(value = "/v1/study-member/{study_id}/{user_id}")
    public ResponseEntity<?> membershipUpdate(
            @RequestBody StudyReqDto.StudyMembershipReqDto studyMembershipReqDto,
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable(name = "study_id") Long study_id,
            @PathVariable(name="user_id") Long user_id
    ){

        System.out.println(study_id);
        System.out.println(user_id);

        StudyResDto.StudyMembershipResDto studyMembershipResDto;

        if (studyMembershipReqDto.getStatus().equals("Approved")){
            // 승인 된 경우
            //StudyMember 테이블 업데이트(상태값 0 -> 1)
            studyService.membershipUpdate(studyMembershipReqDto.getStatus(),
                    study_id, user_id);

            studyMembershipResDto = StudyResDto.StudyMembershipResDto.builder()
                    .id(loginUser.getUser().getId())
                    .study_id(study_id)
                    .user_id(user_id)
                    .status("Approved")
                    .build();

            return new ResponseEntity<>(new ResponseDto<>(1, "가입 신청이 승인되었습니다.",
                    studyMembershipResDto), HttpStatus.OK);
        }
        // 거절 된 경우
        //StudyMember 테이블 업데이트(상태값 0 -> 2)
        studyService.membershipUpdate(studyMembershipReqDto.getStatus(),
                study_id, user_id);

        studyMembershipResDto = StudyResDto.StudyMembershipResDto.builder()
                .id(loginUser.getUser().getId())
                .study_id(study_id)
                .user_id(user_id)
                .status("Rejected")
                .build();

        return new ResponseEntity<>(new ResponseDto<>(1, "가입 신청이 거부되었습니다.",
                studyMembershipResDto), HttpStatus.OK);

    }//memberMembershipApprove()
}
