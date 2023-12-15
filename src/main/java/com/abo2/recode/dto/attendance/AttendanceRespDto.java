package com.abo2.recode.dto.attendance;

import com.abo2.recode.domain.attendance.Attendance;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AttendanceRespDto {

    private Attendance attendance;

    public AttendanceRespDto(Attendance attendance) {
        this.attendance = attendance;
    }

    @Getter
    @Setter
    public static class markAttendanceRespDto {
        private Long id;
        private Long studyId;
        private Long userId;
        private String status;

        @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm")
        private LocalDateTime attendanceDate;

        public markAttendanceRespDto(Attendance attendance){
            this.id = attendance.getId();
            this.studyId = attendance.getStudyRoom().getId();
            this.userId = attendance.getUser().getId();
            this.status = attendance.getStatus() == 0 ? "결석" : "출석";
            this.attendanceDate = attendance.getAttendanceDate();
        }
    }
}


