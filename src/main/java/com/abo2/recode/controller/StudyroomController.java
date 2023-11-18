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


    @Autowired
    StudyService studyService;
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
            @RequestBody StudyReqDto.StudyApplyReqDto studyApplyReqDto
    ){
        //1. study_member에 status = 0으로 insert한다
        logger.info(loginUser.getUser().toString());
        // loginUser.getUser().getId() -> user id 담겨있음

        studyApplyReqDto.setUser_id(loginUser.getUser().getId());
        studyService.studyApply(studyApplyReqDto);

        //2. 성공 return
        ResponseDto<StudyReqDto.StudyApplyReqDto> responseDto
                = new ResponseDto<>(1, "스터디 신청에 성공하였습니다.", studyApplyReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }//studyApply()

    //study 소개 글 조회
    @GetMapping(value = "/study/{study_id}")
    public ResponseEntity<ResponseDto> studyRoomDetailBrowse(
            @PathVariable Long study_id

    ){
        // 1. 요청에 대한 Entity 리턴
        StudyRoom studyRoom
                = studyService.studyRoomDetailBrowse(study_id);
        //1. Studyroom entity -> StudyRoomDetailResDto 담기
        StudyResDto.StudyRoomDetailResDto studyRoomDetailResDto = new StudyResDto.StudyRoomDetailResDto();

        studyRoomDetailResDto.setMaster(studyRoom.getMaster().getId());

        studyRoomDetailResDto.setStudy_room_id(studyRoom.getId());
        studyRoomDetailResDto.setStudy_name(studyRoom.getStudyName());
        studyRoomDetailResDto.setTitle(studyRoom.getTitle());
        studyRoomDetailResDto.setDescription(studyRoom.getDescription());

        studyRoomDetailResDto.setStart_time(convertToString(studyRoom.getStartTime()));
        studyRoomDetailResDto.setEnd_time(convertToString(studyRoom.getEndTime()));

        studyRoomDetailResDto.setStart_date(studyRoom.getStartDate());
        studyRoomDetailResDto.setEnd_date(studyRoom.getEndDate());
        studyRoomDetailResDto.setMax_num(studyRoom.getMaxNum());
        studyRoomDetailResDto.setCreatedAt(studyRoom.getCreatedAt());
        studyRoomDetailResDto.setUpdatedAt(studyRoom.getUpdatedAt());

        //2. 성공 return
        ResponseDto<StudyResDto.StudyRoomDetailResDto> responseDto
                = new ResponseDto<>(1, "스터디 상세 정보입니다.", studyRoomDetailResDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
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
    public ResponseEntity<?> withdrawStudy(@AuthenticationPrincipal LoginUser loginUser, @PathVariable Long study_id){
        studyService.withdrawStudy(loginUser.getUser().getId(), study_id);
        return new ResponseEntity<>(new ResponseDto<>(1, "스터디 탈퇴를 성공하였습니다.", null), HttpStatus.OK);
    }

    // 스터디 목록 조회
    @GetMapping(value = "/main/list")
    public ResponseEntity<?> mainList(){
        List<StudyResDto.StudyListRespDto> studyListRespDto;
        studyListRespDto = studyService.mainList();
        return new ResponseEntity<>(new ResponseDto<>(1, "목록 조회 성공", studyListRespDto), HttpStatus.OK);
    }

}//StudyRoomController class
