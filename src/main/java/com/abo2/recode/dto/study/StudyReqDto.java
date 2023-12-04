package com.abo2.recode.dto.study;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;


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
        private String startTime; //스터디 출석 인정 시작 시간 "12:00"

        @NotEmpty
        private String endTime; //스터디 출석 인정 끝 시간 "12:10"

        @NotEmpty
        private Set<String> attendanceDay; // 출석 인정 요일 - minhee 추가

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
    public static class StudyModifyReqDto{

        @NotEmpty
        private Long createdBy;

        @NotEmpty
        private Long studyId;

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
        private String startTime; //스터디 출석 인정 시작 시간 "12:00"

        @NotEmpty
        private String endTime; //스터디 출석 인정 끝 시간 "12:10"

        @NotEmpty
        private Set<String> attendanceDay; // 출석 인정 요일 - minhee 추가

        @NotEmpty
        private LocalDate startDate;

        @NotEmpty
        private LocalDate endDate;

        @NotEmpty
        private Integer maxNum;

        @NotEmpty
        private LocalDateTime updatedAt;
        //======================================
        // skill 테이블의 스킬들,모집분야
        private String[] skills;
    }

    @Getter
    @Setter
    public static class StudyApplyReqDto{
        //"studyId": 1, // 사용자가 신청하고자 하는 스터디의 ID
        // "userId": 42  // 신청하는 사용자의 ID

        @NotEmpty
        Long studyId;

        @NotEmpty
        Long userId;

        @Builder
        public StudyApplyReqDto(Long studyId, Long userId) {
            this.studyId = studyId;
            this.userId = userId;
        }
    }


    @Getter
    @Setter
    public static class StudyMembershipReqDto{
     /*   {
            "status" : "approved" //"Rejected"
        }*/

        @NotEmpty
        @Size(min = 1, max = 50)
        private String status;

        @Builder
        public StudyMembershipReqDto(String status) {
            this.status = status;
        }

        public StudyMembershipReqDto() {}
    }
}
