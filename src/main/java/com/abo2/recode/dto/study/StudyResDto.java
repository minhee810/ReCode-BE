package com.abo2.recode.dto.study;

import com.abo2.recode.domain.skill.Skill;
import com.abo2.recode.domain.skill.StudySkill;
import com.abo2.recode.domain.studymember.StudyMember;
import com.abo2.recode.domain.studyroom.StudyRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class StudyResDto {

    @Getter
    @Setter
    public static class StudyRoomDetailResDto{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long studyRoomId; //스터디 그룹 일련번호

        @Column(unique = true,nullable = false,length = 50)
        private String studyName; //스터디 그룹 네임

        @Column(nullable = false,length = 50)
        private String title; //스터디 주제

        @Column(nullable = false,length = 300)
        private String description; //스터디 그룹 소개글

        @Column(nullable = false)
        private LocalDate startDate; //스터디 시작 기간

        @Column(nullable = false)
        private LocalDate endDate; //스터디 마무리 기간

        @Column(nullable = false)
        private String startTime; //스터디 출석 인정 시작 시간

        @Column(nullable = false)
        private String endTime; //스터디 출석 인정 끝 시간

        @Column(nullable = false)
        private Integer currentNum = 1; // 필드 선언 시 기본값 지정,스터디 그룹 현재 인원

        @Column(nullable = false)
        private Integer maxNum; //스터디 그룹 전체 티오

        @Column(nullable = false)
        private String master; // 스터디 장

        private List<String> skillNames;

        @CreatedDate
        @Column(nullable = false)
        private LocalDateTime createdAt; //스터디 그룹 만든 시각

        @LastModifiedDate
        @Column(nullable = false)
        private LocalDateTime updatedAt; //스터디 그룹 수정 시각

        @NotEmpty
        private Set<String> attendanceDay; // 출석 인정 요일 - minhee 추가

        public StudyRoomDetailResDto(StudyRoom studyRoom, List<StudySkill> studySkills, Set<String> attendanceDays) {
            this.studyRoomId = studyRoom.getId();
            this.studyName = studyRoom.getStudyName();
            this.title = studyRoom.getTitle();
            this.description = studyRoom.getDescription();
            this.startDate = studyRoom.getStartDate();
            this.endDate = studyRoom.getEndDate();
            this.startTime = String.valueOf(studyRoom.getStartTime());
            this.endTime = String.valueOf(studyRoom.getEndTime());
            this.currentNum = studyRoom.getCurrentNum();
            this.maxNum = studyRoom.getMaxNum();
            this.master = studyRoom.getMaster().getNickname();
            if (studySkills != null) {
                this.skillNames = studySkills.stream()
                        .filter(Objects::nonNull) // 리스트 내 null 객체 필터링
                        .map(StudySkill::getSkill)
                        .filter(Objects::nonNull) // getSkill() 결과가 null이 아닌 것만 필터링
                        .map(Skill::getSkillName)
                        .collect(Collectors.toList());
            } else {
                this.skillNames = new ArrayList<>();
            }
            this.createdAt = studyRoom.getCreatedAt();
            this.updatedAt = studyRoom.getUpdatedAt();
            this.attendanceDay = attendanceDays;
        }

    }

    @Getter
    @Setter
    public static class MyStudyRespDto {
        private Long id;
        private String studyName;
        private String title;
        private String status;
        private List<String> skillNames;

        public MyStudyRespDto(StudyMember studyMember, List<StudySkill> studySkills) {
            StudyRoom studyRoom = studyMember.getStudyRoom();

            this.id = studyRoom.getId();
            this.studyName = studyRoom.getStudyName();
            this.title = studyRoom.getTitle();
            this.status = getStatusString(studyMember.getStatus());

            if (studySkills != null) {
                this.skillNames = studySkills.stream()
                        .filter(Objects::nonNull) // Check if each studySkill object is not null
                        .map(studySkill -> {
                            Skill skill = studySkill.getSkill();
                            return (skill != null) ? skill.getSkillName() : null; // Check if getSkill() is not null
                        })
                        .filter(Objects::nonNull) // Filter out null skill names
                        .collect(Collectors.toList());
            } else {
                this.skillNames = new ArrayList<>(); // Initialize as an empty list if studySkills is null
            }
        }


        private String getStatusString(Integer status) {
            switch (status) {
                case 0:
                    return "대기중";
                case 1:
                    return "열공중";
                case 2:
                    return "거절됨";
                default:
                    return "unknown";
            }
        }
    }

    @Getter
    @Setter
    public static class StudyListRespDto {
        private Long id;
        private String studyName;
        private String title;
        private List<String> skillNames;
        private Integer currentNum;
        private Integer maxNum;
        private String masterEmail;
        private String masterNickname;

        public StudyListRespDto(StudyRoom studyRoom, List<StudySkill> studySkills) {
            this.id = studyRoom.getId();
            this.studyName = studyRoom.getStudyName();
            this.title = studyRoom.getTitle();

            if (studySkills != null) {
                this.skillNames = studySkills.stream()
                        .filter(Objects::nonNull) // Check if studySkill is not null
                        .map(studySkill -> {
                            Skill skill = studySkill.getSkill();
                            return (skill != null) ? skill.getSkillName() : null; // Check if getSkill() is not null
                        })
                        .filter(Objects::nonNull) // Filter out null skill names
                        .collect(Collectors.toList());
            } else {
                this.skillNames = new ArrayList<>(); // Initialize as an empty list if studySkills is null
            }

            this.currentNum = studyRoom.getCurrentNum();
            this.maxNum = studyRoom.getMaxNum();
            if (studyRoom.getMaster() != null) {
                this.masterNickname = studyRoom.getMaster().getNickname();
                this.masterEmail = studyRoom.getMaster().getEmail();
            }
        }
    } //class StudyListRespDto

    @Getter
    @Setter
    public static class StudyMemberListRespDto{

        private Long Id; //스터디 룸 member 일련번호

        private Long studyRoomId;

        private String nickname;

        private Integer status;

        @Builder
        public StudyMemberListRespDto(Long id, Long studyRoomId, String nickname, Integer status) {
            Id = id;
            this.studyRoomId = studyRoomId;
            this.nickname = nickname;
            this.status = status;
        }
    }//StudyMemberListRespDto

    @Getter
    @Setter
    public static class StudyMemberAndStatusListRespDto{

        private Long userId;

        private Long studyRoomId;

        private String username;

        private Integer createdBy;

        @Builder
        public StudyMemberAndStatusListRespDto(Long userId, Long studyRoomId, String username, Integer createdBy) {
            this.userId = userId;
            this.studyRoomId = studyRoomId;
            this.username = username;
            this.createdBy = createdBy;
        }
    }//StudyMemberAndStatusListRespDto

    @Getter
    @Setter
    public static class StudyRoomApplyResDto{
     /*   {
            "code": 1,
                "msg": "스터디 신청에 성공하였습니다.",
                "data": {
            "studyId": 1
        }
        }*/

        @NotEmpty
        private Long studyId;

        @Builder
        public StudyRoomApplyResDto(Long studyId) {
            this.studyId = studyId;
        }
    }

//    @Getter
//    @Setter
//    public static class StudyRoomCreateResDto{
//
//        private Long id;
//        private String study_name;
//        private String title;
//        private List<String> skillNames;
//        private Integer current_num;
//        private Integer max_num;
//        private String masterEmail;
//        private String masterNickname;
//
//    }

    @Getter
    @Setter
    public static class StudyCreateRespDto {

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

        @NotNull
        private LocalTime startTime; //스터디 출석 인정 시작 시간 " 12:00"

        @NotNull
        private LocalTime endTime; //스터디 출석 인정 끝 시간 " 12:10"

        @NotEmpty
        private Set<String> attendanceDay; // 출석 인정 요일 - minhee 추가

        @NotNull
        private LocalDate startDate;

        @NotNull
        private LocalDate endDate;

        @NotEmpty
        private Integer maxNum;

        @NotEmpty
        private Long userId; // 별도로 가져오는 코드 필요(입력 받지 않음)

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;
        //======================================


        // 선택된 Skill 들의 이름 리스트
//        private Set<StudyReqDto.SkillDto> skills;

        @NotEmpty
        private List<String> skillNames;
//        private String[] skillNames;

        @Builder
        public StudyCreateRespDto(String studyName, String title, String description,
                                  LocalTime startTime, LocalTime endTime, Set<String> attendanceDay,
                                  LocalDate startDate, LocalDate endDate, Integer maxNum, Long userId,
                                  LocalDateTime createdAt, LocalDateTime updatedAt, List<String> skillNames) {
            this.studyName = studyName;
            this.title = title;
            this.description = description;
            this.startTime = startTime;
            this.endTime = endTime;
            this.attendanceDay = attendanceDay;
            this.startDate = startDate;
            this.endDate = endDate;
            this.maxNum = maxNum;
            this.userId = userId;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.skillNames = skillNames;
        }
    }

    // skill 객체를 받아오기 위한
    @Getter
    @Setter
    public class SkillDto{
        @NotEmpty
        private String skillName;

    }


    @Getter
    @Setter
    public static class StudyMembershipResDto{
      /*  {
            "code":1
            "msg": "가입 신청이 승인되었습니다."   // or "가입 신청이 거부되었습니다."
            "data": {
            "id": 2
            "studyId": 1
            "userId": 2
            "status": "approved" // or "rejected"
        }
        }*/

        @NotEmpty
        private Long id; //나(승인하는 스터디 조장)

        @NotEmpty
        private Long userId;

        @NotEmpty
        private Long studyId;

        @NotEmpty
        private String status;

        @Builder
        public StudyMembershipResDto(Long id, Long userId, Long studyId, String status) {
            this.id = id;
            this.userId = userId;
            this.studyId = studyId;
            this.status = status;
        }
    }//StudyMembershipResDto

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApplicationResDto{

        @NotEmpty
        private Long userId;

        @NotEmpty
        private String username;

        @NotEmpty
        private Integer status;

        @NotEmpty
        private String email;

    }//ApplicationResDto

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApplicationEssayResDto{

        @NotEmpty
        private String username;

        @NotEmpty
        private String email;

        @NotEmpty
        private String essay;
    }//class ApplicationEssayResDto

    @Getter
    @Setter
    public static class CheckStudyMaserRespDto{
        private String username;
        private String masterNickname;
    }

}

