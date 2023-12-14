package com.abo2.recode.dto.attendance;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class AttendanceReqDto {

    @Getter
    @Setter
    public static class markAttendanceReqDto {
        private Long id;
        private Long studyId;
        private Long userId;
        private LocalDateTime attendanceDate;
        private String status;

    }
}
