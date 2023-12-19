package com.abo2.recode.domain.attendance;

import com.abo2.recode.domain.studyroom.StudyRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
public class AttendanceDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_day_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "study_room_id")
    private StudyRoom studyRoom;

    @Column(nullable = false)
    private String attendanceDay;    // 각 스터디 마다의 출석 요일


    @Builder
    public AttendanceDay(Long id, StudyRoom studyRoom, String attendanceDay) {
        this.id = id;
        this.studyRoom = studyRoom;
        this.attendanceDay = attendanceDay;
    }
}