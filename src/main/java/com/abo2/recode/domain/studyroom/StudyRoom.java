package com.abo2.recode.domain.studyroom;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class StudyRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String study_name;

    @Column(unique = true, nullable = false, length = 50)
    private String title;

    @Column(unique = true, nullable = false, length = 300)
    private String description;

    @Column(unique = true, nullable = false)
    private LocalDateTime start_date;

    @Column(unique = true, nullable = false)
    private LocalDateTime end_date;

    @Column(unique = true, nullable = false)
    private LocalDateTime start_time;

    @Column(unique = true, nullable = false)
    private LocalDateTime end_time;

    @Column(unique = true, nullable = false)
    private Integer current_num;

    @Column(unique = true, nullable = false)
    private Integer max_num;

    @Column(unique = true, nullable = false)
    private String create_by;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;


    @Builder
    public StudyRoom(Long id, String study_name, String title, String description, LocalDateTime start_date, LocalDateTime end_date, LocalDateTime start_time, LocalDateTime end_time, Integer current_num, Integer max_num, String create_by, LocalDateTime createdAt, LocalDateTime updatedAt) {
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
        this.create_by = create_by;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
