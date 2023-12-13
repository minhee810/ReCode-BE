package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.attendance.AttendanceRespDto;
import com.abo2.recode.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AttendanceController {

    private final AttendanceService attendanceService;


    @PostMapping(value = "/v1/study/{studyId}/attendance")
    public ResponseEntity<?> markAttendance(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable Long studyId,
            @RequestParam String attendanceDate) {

        // 출석체크 날짜 문자열을 LocalDateTime으로 변환
        LocalDateTime parsedAttendanceDate = LocalDateTime.parse(attendanceDate);

        // 출석체크 서비스를 호출하여 출석체크를 처리
        AttendanceRespDto markAttendance = attendanceService.markAttendance(loginUser.getUser().getId(), studyId, parsedAttendanceDate);

        return new ResponseEntity<>(new ResponseDto<>(1, "출석체크를 완료했습니다.", markAttendance), HttpStatus.CREATED);
    }
}

