package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.domain.studymember.StudyMember;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserRepository;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.study.StudyReqDto;
import com.abo2.recode.dto.study.StudyResDto;
import com.abo2.recode.handler.ex.CustomApiException;
import com.abo2.recode.service.StudyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class StudyroomController {

    private static final Logger logger = LoggerFactory.getLogger(StudyroomController.class);

    StudyService studyService;

    private UserRepository userRepository;

    @Autowired
    public StudyroomController(StudyService studyService, UserRepository userRepository) {
        this.studyService = studyService;
        this.userRepository = userRepository;
    }

    @CrossOrigin
    @Transactional
    @PostMapping(value = "/v1/study") // @AuthenticationPrincipal 에서 LoginUser 객체를 꺼내와야 함. LoginUSer
    public ResponseEntity<?> createRoom(@AuthenticationPrincipal LoginUser loginUser,
                                        @RequestBody StudyReqDto.StudyCreateReqDto studyCreateReqDto, BindingResult bindingResult) {


        // 스터디 이름이 중복되지 않는지 확인 필요
        studyCreateReqDto.setUserId(loginUser.getUser().getId());

        //1. studyReqDto를 DB에 넣기 Service에서 처리
        StudyResDto.StudyCreateRespDto studyCreateRespDto = studyService.createRoom(studyCreateReqDto);


        new ResponseDto<>(HttpStatus.OK.value(), "Success", studyCreateReqDto);

        return new ResponseEntity<>(new ResponseDto<>(1, "Success", studyCreateRespDto), HttpStatus.OK);

    }

    //StudyRoom 모집 글 수정 = 스터디 그룹 정보 수정
    @PutMapping(value = "/v1/study/{studyId}/modify")
    public ResponseEntity<?> modifyRoom(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable(name = "studyId") Long studyId,
            @RequestBody StudyReqDto.StudyModifyReqDto studyModifyReqDto
    ) {
        // 수정할 스터디 룸 정보,조장 아이디 정보 입력
        studyModifyReqDto.setStudyId(studyId);
        studyModifyReqDto.setCreatedBy(loginUser.getUser().getId());

        //1. studyModifyReqDto를 DB에 넣기 Service에서 처리
        StudyResDto.StudyCreateRespDto studyCreateRespDto = studyService.modifyRoom(studyModifyReqDto);

        //2. 성공 return
        return new ResponseEntity<>(new ResponseDto<>(1, "스터디 수정에 성공하였습니다.", studyCreateRespDto), HttpStatus.OK);
    }

    //study 가입 신청
    @PostMapping(value = "/v1/study/{studyId}/apply")
    public ResponseEntity<ResponseDto> studyApply(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable Long studyId
    ) {
        //"studyId": 1, // 사용자가 신청하고자 하는 스터디의 ID
        // "userId": 42  // 신청하는 사용자의 ID

        //1. study_member에 status = 0으로 insert한다
        StudyReqDto.StudyApplyReqDto studyApplyReqDto =
                new StudyReqDto.StudyApplyReqDto(studyId, loginUser.getUser().getId());

        StudyResDto.StudyRoomApplyResDto studyRoomApplyResDto = studyService.studyApply(studyApplyReqDto);

        //2. 성공 return
        return new ResponseEntity<>(new ResponseDto<>(1, "스터디 신청에 성공하였습니다.", studyRoomApplyResDto), HttpStatus.OK);
    }

    //study 소개 글 조회
    @GetMapping(value = "/study/{studyId}")
    public ResponseEntity<?> studyRoomDetailBrowse(@PathVariable Long studyId) {
        // 1. 요청에 대한 Entity 리턴
        StudyResDto.StudyRoomDetailResDto studyRoomDetailResDto =
                studyService.studyRoomDetailBrowse(studyId);

        //2. 성공 return
        return new ResponseEntity<>(new ResponseDto<>(1, "스터디 상세 정보입니다.", studyRoomDetailResDto), HttpStatus.OK);
    }

    // 스터디 룸 탈퇴
    @PostMapping(value = "/v1/study/{studyId}/withdraw")
    public ResponseEntity<?> withdrawStudy(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable(value = "studyId") Long studyId
    ) {
        studyService.withdrawStudy(loginUser.getUser().getId(), studyId);
        return new ResponseEntity<>(new ResponseDto<>(1, "스터디 탈퇴를 성공하였습니다.", null), HttpStatus.OK);
    }

    // 스터디 목록 조회
    @GetMapping(value = "/main/list")
    public ResponseEntity<?> mainList(@RequestParam(required = false) String title,
                                      @RequestParam(required = false) String studyName) {
        List<StudyResDto.StudyListRespDto> studyListRespDto;
        if (title != null && !title.isEmpty()) {
            studyListRespDto = studyService.findStudyRoomByTile(title);
        } else if (studyName != null && !studyName.isEmpty()) {
            studyListRespDto = studyService.findStudyRoomByStudyName(studyName);
        } else {
            studyListRespDto = studyService.mainList();
        }
        return new ResponseEntity<>(new ResponseDto<>(1, "목록 조회 성공", studyListRespDto), HttpStatus.OK);
    }

    // 스터디룸 관리 화면에서 신청 현황 멤버 목록 불러오기
    @GetMapping(value = "/study-groups/{groupId}/applications")
    public ResponseEntity<?> applications(
            @PathVariable(name = "groupId") Long groupId
    ) {

        List<StudyResDto.ApplicationResDto> applicationResDtoList = studyService.applications(groupId);

        return new ResponseEntity<>(new ResponseDto<>(1, "신청 인원 목록을 성공적으로 조회했습니다.", applicationResDtoList)
                , HttpStatus.OK);
    }

    // 스터디룸 관리 화면에서 신청 현황 멤버의 에세이 조회
    @GetMapping(value = "/v1/study-groups/{groupId}/applications/{userId}")
    public ResponseEntity<?> applicationsEssay(
            @PathVariable(name = "groupId") Long groupId,
            @PathVariable(name = "userId") Long userId
    ) {
        StudyResDto.ApplicationEssayResDto applicationEssayResDto =
                studyService.applicationsEssay(groupId, userId);

        return new ResponseEntity<>(new ResponseDto<>(1, "신청 인원의 자기소개서를 성공적으로 조회했습니다.", applicationEssayResDto)
                , HttpStatus.OK);
    }


    // 스터디 그룹에서 멤버 목록 불러오기 +(찬:Study_member의 status 역시 고려하여 가입 승인 된 스터디멤버만 조회하도록 수정)
    @GetMapping(value = "/v1/study/{studyId}/memberlist")
    public ResponseEntity<?> getsStudyMembers(@PathVariable("studyId") Long studyRoomId) {
        List<StudyMember> studyMembers = studyService.getStudyMembersByRoomId(studyRoomId);

        logger.info(studyMembers.toString());

        List<StudyResDto.StudyMemberListRespDto> studyMemberListRespDtoList = getStudyMemberListRespDtos(studyMembers);

        if (studyMembers.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(new ResponseDto<>
                    (1, "스터디 그룹 멤버 목록입니다.", studyMemberListRespDtoList), HttpStatus.OK);
        }

    }

    private List<StudyResDto.StudyMemberListRespDto> getStudyMemberListRespDtos(List<StudyMember> studyMembers) {
        List<StudyResDto.StudyMemberListRespDto> studyMemberListRespDtoList = new ArrayList<>();

        for (StudyMember studyMember : studyMembers) {
            StudyResDto.StudyMemberListRespDto studyMemberListRespDto
                    = StudyResDto.StudyMemberListRespDto.builder()
                    .id(studyMember.getId())
                    .studyRoomId(studyMember.getStudyRoom().getId())
                    .nickname(studyMember.getUser().getNickname())
                    .status(studyMember.getStatus())
                    .build();

            studyMemberListRespDtoList.add(studyMemberListRespDto);

        }
        return studyMemberListRespDtoList;
    }


    //스터디 그룹에서 멤버 강퇴 + (찬:강제 퇴출하는 사람이 조장이 맞는지 체크하는 로직 추가)
    @DeleteMapping(value = "/v1/{studyId}/member/{member_id}")
    public ResponseEntity<?> deleteMember(@AuthenticationPrincipal LoginUser loginUser,
                                          @PathVariable("studyId") Long studyRoomId,
                                          @PathVariable("member_id") Long memberId) {

        StudyMember studyMember
                = studyService.deleteMember(loginUser.getUser().getId(), studyRoomId, memberId);

        StudyResDto.StudyMemberListRespDto studyMemberListRespDto
                = StudyResDto.StudyMemberListRespDto.builder()
                .id(studyMember.getId())
                .studyRoomId(studyMember.getStudyRoom().getId())
                .nickname(studyMember.getUser().getNickname())
                .status(studyMember.getStatus())
                .build();

        return new ResponseEntity<>(new ResponseDto<>(1, "해당 멤버를 내보냈습니다.", studyMemberListRespDto), HttpStatus.OK);
    }


    // 해당 스터디의 스터디장인지 체크
    @GetMapping(value = "/v1/study/{studyId}/check-master")
    public ResponseEntity<?> checkMaster(@AuthenticationPrincipal LoginUser loginUser,
                                         @PathVariable("studyId") Long studyRoomId) {
        Long userId = loginUser.getUser().getId();
        Optional<User> userOpt = userRepository.findById(loginUser.getUser().getId());

        if (!userOpt.isPresent()) {
            return ResponseEntity.badRequest().body(new ResponseDto<>(-1, "해당 유저가 없습니다.", null));
        }

        Long masterId = studyService.findcreatedByBystudyId(studyRoomId);
        if (masterId == null) {
            return ResponseEntity.badRequest().body(new ResponseDto<>(-1, "해당 스터디가 존재하지 않습니다.", null));
        }

        if (!userId.equals(masterId)) {
            throw new CustomApiException("해당 스터디의 스터디 조장이 아닙니다.");
        }

        User master = userOpt.get();
        StudyResDto.CheckStudyMaserRespDto checkStudyMasterRespDto = new StudyResDto.CheckStudyMaserRespDto();
        checkStudyMasterRespDto.setUsername(master.getUsername());
        checkStudyMasterRespDto.setMasterNickname(master.getNickname());

        return new ResponseEntity<>(new ResponseDto<>(1, "해당 스터디의 스터디 조장입니다.", checkStudyMasterRespDto), HttpStatus.OK);
    }


}
