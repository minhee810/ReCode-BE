package com.abo2.recode.domain.studyroom;


import com.abo2.recode.domain.post.Post;
import com.abo2.recode.domain.quiz.Quiz;
import com.abo2.recode.domain.skill.StudySkill;
import com.abo2.recode.domain.studymember.StudyMember;
import com.abo2.recode.domain.user.User;
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
    @Column(name="study_room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(nullable = false)
    private Integer currentNum = 1; // 필드 선언 시 기본값 지정,스터디 그룹 현재 인원

    @Column(nullable = false)
    private Integer maxNum; //스터디 그룹 전체 티오

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by")
    private User master;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt; //스터디 그룹 만든 시각

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt; //스터디 그룹 수정 시각

    //==========================================

    // StudyRoom을 참조하는 StudySkill 엔티티
    @OneToMany(mappedBy = "studyRoom", cascade = CascadeType.ALL)
    private List<StudySkill> studySkills;

    // StudyRoom을 참조하는 Study_Member 엔티티
    @OneToMany(mappedBy = "studyRoom", cascade = CascadeType.ALL)
    private List<StudyMember> studyMembers;

    // StudyRoom을 참조하는 attendance 엔티티
    @OneToMany(mappedBy = "studyRoom", cascade = CascadeType.ALL)
    private List<Attendance> attendanceList = new ArrayList<>();

    // StudyRoom을 참조하는 quiz 엔티티
    @OneToMany(mappedBy = "studyRoom", cascade = CascadeType.ALL)
    private List<Quiz> quizzes = new ArrayList<>();

    // StudyRoom을 참조하는 Post 엔티티
    @OneToMany(mappedBy = "studyRoom", cascade = CascadeType.ALL)
    private List<Post> posts;

    //===========================================

    @Builder
    public StudyRoom(Long id, String studyName, String title, String description, LocalDate startDate,
                     LocalDate endDate, LocalDateTime startTime, LocalDateTime endTime,
                     Integer currentNum, Integer maxNum, User master) {

        this.id = id;
        this.studyName = studyName;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.currentNum = currentNum;
        this.maxNum = maxNum;
        this.master = master;
    }

}
