package com.abo2.recode.domain.attendanceDay;

import com.abo2.recode.domain.studyroom.StudyRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AttendanceDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendanceDayId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "studyId")
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
