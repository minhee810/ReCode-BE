package com.abo2.recode.domain.studyroom;

import antlr.collections.impl.LList;
import com.abo2.recode.domain.quiz.Quiz;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class StudyRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //스터디 그룹 일련번호

    @Column(unique = true,nullable = false,length = 50)
    private String study_name; //스터디 그룹 네임

    @Column(nullable = false,length = 50)
    private String title; //스터디 주제

    @Column(nullable = false,length = 300)
    private String description; //스터디 그룹 소개글

    @Column(nullable = false)
    private LocalDate start_date; //스터디 시작 기간

    @Column(nullable = false)
    private LocalDate end_date; //스터디 마무리 기간

    @Column(nullable = false)
    private LocalDateTime start_time; //스터디 출석 인정 시작 시간

    @Column(nullable = false)
    private LocalDateTime end_time; //스터디 출석 인정 끝 시간

    @Column(nullable = false)
    private Integer current_num = 1; // 필드 선언 시 기본값 지정,스터디 그룹 현재 인원

    @Column(nullable = false)
    private Integer max_num; //스터디 그룹 전체 티오

    @Column(nullable = false)
    private Long created_By; // 스터디 장

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt; //스터디 그룹 만든 시각

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt; //스터디 그룹 수정 시각

    @OneToMany
    @JoinColumn(name = "quiz_id")
    private List<Quiz> quizzes = new ArrayList<>();

    @Builder
    public StudyRoom(String study_name, String title, String description, LocalDate start_date,
                     LocalDate end_date, LocalDateTime start_time, LocalDateTime end_time,
                     Integer current_num, Integer max_num, Long created_By) {

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
    }
}
