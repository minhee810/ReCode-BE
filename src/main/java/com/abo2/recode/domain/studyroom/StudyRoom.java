package com.abo2.recode.domain.studyroom;

import com.abo2.recode.domain.skill.StudySkill;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class StudyRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studyRoom_id")
    private Long id; //스터디 그룹 일련번호

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
    private LocalDateTime startTime; //스터디 출석 인정 시작 시간

    @Column(nullable = false)
    private LocalDateTime endTime; //스터디 출석 인정 끝 시간

    @ColumnDefault("1")
    private Integer currentNum; // 필드 선언 시 기본값 지정,스터디 그룹 현재 인원

    @OneToMany(mappedBy = "studyRoom")
    private List<StudySkill> skills;  // 연관관계

    @Column(nullable = false)
    private Integer maxNum; //스터디 그룹 전체 티오

    @Column(nullable = false)
    private Long createdBy; // 스터디 장

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt; //스터디 그룹 만든 시각

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt; //스터디 그룹 수정 시각


    @Builder
    public StudyRoom(String studyName, String title, String description, LocalDate startDate,
                     LocalDate endDate, LocalDateTime startTime, LocalDateTime endTime,
                     Integer currentNum, Integer maxNum, Long createdBy) {

        this.studyName = studyName;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.currentNum = currentNum;
        this.maxNum = maxNum;
        this.createdBy = createdBy;
    }

}
