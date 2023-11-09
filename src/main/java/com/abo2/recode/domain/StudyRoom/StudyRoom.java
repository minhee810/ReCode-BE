package com.abo2.recode.domain.StudyRoom;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class StudyRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false,length = 50)
    private String study_name; //스터디 그룹 네임

    @Column(unique = true,nullable = false,length = 50)
    private String title; //스터디 주제

    @Column(unique = true,nullable = false,length = 300)
    private String description; //스터디 그룹 소개글

    @Column(unique = true,nullable = false)
    private LocalDateTime start_date; //스터디 시작 기간

    @Column(unique = true,nullable = false)
    private LocalDateTime end_date; //스터디 마무리 기간

    @Column(unique = true,nullable = false)
    private LocalDateTime start_time; //스터디 출석 인정 시작 시간

    @Column(unique = true,nullable = false)
    private LocalDateTime end_time; //스터디 출석 인정 끝 시간

    @Column(unique = true,nullable = false)
    private Integer current_num; //스터디 그룹 현재 인원

    @Column(unique = true,nullable = false)
    private Integer max_num; //스터디 그룹 전체 티오

    @Column(unique = true,nullable = false)
    private String created_By; // 스터디 장

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt; //스터디 그룹 만든 시각

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt; //스터디 그룹 수정 시각

    @Builder
    public StudyRoom(Long id, String study_name, String title, String description, LocalDateTime start_date, LocalDateTime end_date, LocalDateTime start_time, LocalDateTime end_time, Integer current_num, Integer max_num, String created_By, LocalDateTime createdAt, LocalDateTime updatedAt) {

        this.id = id;
        this.study_name = study_name;
        this.title = title;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.current_num = current_num;
        this.max_num = max_num;
        this.created_By = created_By;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
