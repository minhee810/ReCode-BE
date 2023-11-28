package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.domain.studyroom.StudyRoom;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping(value = "/api")
public class StudyroomController {

    private static final Logger logger = LoggerFactory.getLogger(StudyroomController.class);

    StudyService studyService;
    @Autowired
    public StudyroomController(StudyService studyService) {
        this.studyService = studyService;
    }

    @CrossOrigin
    @Transactional
    @PostMapping(value="/v1/study") // @AuthenticationPrincipal 에서 LoginUser객체를 꺼내와야 함. LoginUSer
    public ResponseEntity<ResponseDto> createRoom(@AuthenticationPrincipal LoginUser loginUser,
                                                  @RequestBody StudyReqDto.StudyCreateReqDto studyCreateReqDto){

        logger.info(loginUser.getUser().toString());
        // loginUser.getUser().getId() -> user id 담겨있음
        studyCreateReqDto.setUserId(loginUser.getUser().getId());
        System.out.println("User : "+loginUser.getUser().getId());

        //1. studyReqDto를 DB에 넣기 Service에서 처리
        studyService.createRoom(studyCreateReqDto);

        ResponseDto<StudyReqDto.StudyCreateReqDto> responseDto
                = new ResponseDto<>(HttpStatus.OK.value(), "Success", studyCreateReqDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }//createRoom()

    //study 가입 신청
    @PostMapping(value="/v1/study/{study_id}/apply")
    public ResponseEntity<ResponseDto> studyApply(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable Long study_id,
            @RequestBody StudyReqDto.StudyApplyReqDto studyApplyReqDto
    ){
        //"study_id": 1, // 사용자가 신청하고자 하는 스터디의 ID
        // "user_id": 42  // 신청하는 사용자의 ID

        //1. study_member에 status = 0으로 insert한다

        studyApplyReqDto.setStudy_id(study_id);
        studyApplyReqDto.setUser_id(loginUser.getUser().getId());

        StudyResDto.StudyRoomApplyResDto studyRoomApplyResDto = studyService.studyApply(studyApplyReqDto);

        //2. 성공 return
        ResponseDto<StudyResDto.StudyRoomApplyResDto> responseDto
                = new ResponseDto<>(1, "스터디 신청에 성공하였습니다.", studyRoomApplyResDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }//studyApply()

    //study 소개 글 조회
    @GetMapping(value = "/study/{study_room_id}")
    public ResponseEntity<?> studyRoomDetailBrowse(@PathVariable Long study_room_id){
        // 1. 요청에 대한 Entity 리턴
        StudyResDto.StudyRoomDetailResDto studyRoomDetailResDto =
                studyService.studyRoomDetailBrowse(study_room_id);

        //2. 성공 return
        return new ResponseEntity<>(new ResponseDto<>(1, "스터디 상세 정보입니다.", studyRoomDetailResDto), HttpStatus.OK);
    } //studyRoomDetailBrowse()

    // LocalDateTime 객체를 "Day Hour:Minute" 형식의 문자열로 변환
    private static String convertToString(LocalDateTime dateTime) {
        String dayOfWeek = dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();

        return dayOfWeek + " " + String.format("%02d:%02d", hour, minute);
    }//convertToString()

    // 스터디 룸 탈퇴
    @PostMapping(value = "/v1/study/{study_id}/withdraw/{user_id}")
    public ResponseEntity<?> withdrawStudy(@AuthenticationPrincipal LoginUser loginUser, @PathVariable Long study_room_id){
        studyService.withdrawStudy(loginUser.getUser().getId(), study_room_id);
        return new ResponseEntity<>(new ResponseDto<>(1, "스터디 탈퇴를 성공하였습니다.", null), HttpStatus.OK);
    }

    // 스터디 목록 조회
    @GetMapping(value = "/main/list")
    public ResponseEntity<?> mainList(){
        List<StudyResDto.StudyListRespDto> studyListRespDto;
        studyListRespDto = studyService.mainList();
        return new ResponseEntity<>(new ResponseDto<>(1, "목록 조회 성공", studyListRespDto), HttpStatus.OK);
    }//mainList()
    
    // 스터디룸 관리 화면에서 신청 현황 멤버 목록 불러오기
    @GetMapping(value = "/study-groups/{groupId}/applications")
    public ResponseEntity<?> applications(
            @PathVariable(name = "groupId") Long groupId
    ){

        List<StudyResDto.ApplicationResDto> applicationResDtoList = studyService.applications(groupId);

        return new ResponseEntity<>(new ResponseDto<>(1,"신청 인원 목록을 성공적으로 조회했습니다.",applicationResDtoList)
                ,HttpStatus.OK);
    }

    // 스터디룸 관리 화면에서 신청 현황 멤버의 에세이 조회
    @GetMapping(value = "/study-groups/{groupId}/applications/{user_id}")
    public ResponseEntity<?> applicationsEssay(
            @PathVariable(name = "groupId") Long groupId,
            @PathVariable(name = "user_id") Long user_Id
    ){
        StudyResDto.ApplicationEssayResDto applicationEssayResDto =
                studyService.applicationsEssay(groupId,user_Id);

        return new ResponseEntity<>(new ResponseDto<>(1,"신청 인원의 자기소개서를 성공적으로 조회했습니다.",applicationEssayResDto)
                ,HttpStatus.OK);
    }

}//StudyRoomController class
