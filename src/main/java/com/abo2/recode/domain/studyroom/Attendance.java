package com.abo2.recode.domain.studyroom;

import com.abo2.recode.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Attendance {

//    Table Attendance {
//        attendance_id integer [primary key]
//        study_room_id integer
//        user_id integer
//        attendance_date timestamp
//        status integer //0 : 결석 1: 출석
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private Long id;

    // @JoinColumn(name = "parent_id", referencedColumnName = "id", onDelete = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "study_room_id")
    private StudyRoom studyRoom;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime attendance_date; //출석일

    @Column(nullable = false)
    private Integer status; //0 : 결석 1: 출석

}
