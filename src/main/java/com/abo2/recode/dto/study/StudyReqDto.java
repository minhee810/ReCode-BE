package com.abo2.recode.dto.study;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class StudyReqDto {

    @Getter
    @Setter
    public static class StudyCreateReqDto{

        // Study_Room
        @NotEmpty
        @Size(min = 1, max = 50)
        private String study_name;

        @NotEmpty
        @Size(min = 1, max = 50)
        private String title;

        @NotEmpty
        @Size(min = 1, max = 300)
        private String description;

        @NotEmpty
        private String start_time; //스터디 출석 인정 시작 시간 "monday 12:00"

        @NotEmpty
        private String end_time; //스터디 출석 인정 끝 시간 "monday 12:10"

        @NotEmpty
        private LocalDate start_date;

        @NotEmpty
        private LocalDate end_date;

        @NotEmpty
        private Integer max_num;

        @NotEmpty
        private Long user_id; //별도로 가져오는 코드 필요(입력 받지 않음)

        @NotEmpty
        private LocalDateTime createdAt;

        @NotEmpty
        private LocalDateTime updatedAt;
        //======================================
        // skill 테이블의 스킬들,모집분야
        private String[] skills;



    }
}
