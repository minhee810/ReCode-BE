package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.domain.attendance.Attendance;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.attendance.AttendanceReqDto;
import com.abo2.recode.dto.attendance.AttendanceRespDto;
import com.abo2.recode.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

        if (markAttendanceReqDto != null && markAttendanceReqDto.getStatus().equals("Checked")) {
            // 승인 된 경우
            // attendance 테이블 업데이트(상태값 0 -> 1)
            AttendanceRespDto.markAttendanceRespDto markAttendanceRespDto =
                    attendanceService.markAttendance(markAttendanceReqDto.getStatus(), loginUser.getUser().getId(), studyId, markAttendanceReqDto);
            return new ResponseEntity<>(new ResponseDto<>(1, "출석체크를 완료했습니다.", markAttendanceRespDto), HttpStatus.CREATED);
        } else {
            // 승인되지 않은 경우 혹은 markAttendanceReqDto 가 null 인 경우
            return new ResponseEntity<>(new ResponseDto<>(-1, "출석체크 요청이 잘못되었습니다.", null), HttpStatus.BAD_REQUEST);

        }
    }

}

