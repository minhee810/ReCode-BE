package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.attendance.AttendanceReqDto;
import com.abo2.recode.dto.attendance.AttendanceResDto;
import com.abo2.recode.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AttendanceController {

    private final AttendanceService attendanceService;


    @PostMapping(value = "/v1/study/{studyId}/attendance")
    public ResponseEntity<?> markAttendance(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable Long studyId,
            @RequestBody AttendanceReqDto.markAttendanceReqDto markAttendanceReqDto) {

        AttendanceResDto.markAttendanceResDto markAttendanceRespDto =
                attendanceService.markAttendance(markAttendanceReqDto.getStatus(), loginUser.getUser().getId(), studyId, markAttendanceReqDto);

        if (markAttendanceReqDto != null && markAttendanceReqDto.getStatus().equals("Checked")) {
            // 승인 된 경우
            // attendance 테이블 업데이트(상태값 0 -> 1)
            return new ResponseEntity<>(new ResponseDto<>(1, "출석체크를 완료했습니다.", markAttendanceRespDto), HttpStatus.CREATED);

        } else {
            return new ResponseEntity<>(new ResponseDto<>(-1, "출석체크 요청이 잘못되었습니다.", markAttendanceRespDto), HttpStatus.BAD_REQUEST);

        }
    }

}