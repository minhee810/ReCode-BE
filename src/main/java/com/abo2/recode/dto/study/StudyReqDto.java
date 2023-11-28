package com.abo2.recode.dto.study;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class StudyReqDto {

    @Getter
    @Setter
    public static class StudyCreateReqDto{

        // Study_Room
        @NotEmpty
        @Size(min = 1, max = 50)
        private String studyName;

        @NotEmpty
        @Size(min = 1, max = 50)
        private String title;

        @NotEmpty
        @Size(min = 1, max = 300)
        private String description;

        @NotEmpty
        private String startTime; //스터디 출석 인정 시작 시간 "monday 12:00"

        @NotEmpty
        private String endTime; //스터디 출석 인정 끝 시간 "monday 12:10"

        @NotEmpty
        private List<String> allowedDays; // 출석 인정 요일 - minhee 추가

        @NotEmpty
        private LocalDate startDate;

        @NotEmpty
        private LocalDate endDate;

        @NotEmpty
        private Integer maxNum;

        @NotEmpty
        private Long userId; // 별도로 가져오는 코드 필요(입력 받지 않음)

        @NotEmpty
        private LocalDateTime createdAt;

        @NotEmpty
        private LocalDateTime updatedAt;
        //======================================
        // skill 테이블의 스킬들,모집분야
        private String[] skills;



    }

    @Getter
    @Setter
    public static class StudyApplyReqDto{
        //"study_id": 1, // 사용자가 신청하고자 하는 스터디의 ID
        // "user_id": 42  // 신청하는 사용자의 ID

        @NotEmpty
        Long study_id;

        @NotEmpty
        Long user_id;
    }
}
