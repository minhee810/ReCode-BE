package com.abo2.recode.dto.attendance;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

public class AttendanceReqDto {

    @Getter
    @Setter
    public static class markAttendanceReqDto {
        private Long studyId;
        private Long userId;
        private String status;

        @CreatedDate
        private LocalDateTime attendanceDate;
    }
}