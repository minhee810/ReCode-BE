package com.abo2.recode.dto.quiz;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class QuizReqDto {

    @Getter
    @Setter
    public static class QuizWriteReqDto {
        private Long quizId;
        private String title;
        private Integer difficulty;
        private String quiz_link;
        private Long studyRoomId;
        private Long userId;
        private LocalDate createdAt;
    }
}
