package com.abo2.recode.dto.quiz;

import com.abo2.recode.domain.quiz.Quiz;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class QuizReqDto {

    /*
        QUIZ_ID  	DIFFICULTY  	QUIZ_LINK  	TITLE  	studyId  	userId
    */
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
