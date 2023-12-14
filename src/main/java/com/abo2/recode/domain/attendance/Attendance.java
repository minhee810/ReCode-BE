package com.abo2.recode.domain.attendance;

import com.abo2.recode.domain.studyroom.StudyRoom;
import com.abo2.recode.domain.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "study_room_id")
    private StudyRoom studyRoom;  // 스터디룸 아이디

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 사용자의 아이디

    @CreatedDate
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm")
    private LocalDateTime attendanceDate; //출석일 : localDatetime 과 비교 해야함.


    @Column(nullable = false)
    private Integer status; //0 : 결석 1: 출석

}