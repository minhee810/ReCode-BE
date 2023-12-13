package com.abo2.recode.dto.attendance;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AttendanceRespDto {
    private Long id;
    private Long studyId;
    private Long userId;
    private LocalDateTime attendanceDate;
    private Long status;
}
