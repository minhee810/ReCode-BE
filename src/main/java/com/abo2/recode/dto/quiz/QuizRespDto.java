package com.abo2.recode.dto.quiz;

import com.abo2.recode.domain.quiz.Quiz;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class QuizRespDto {

    @Getter
    @Setter
    public static class QuizWriteRespDto {
        private Long id;
        private String title;
        private Integer difficulty;
        private String quiz_link;
        private String nickname;
        private LocalDateTime created_At;


        public QuizWriteRespDto(Quiz quiz) {
            this.id = quiz.getId();
            this.title = quiz.getTitle();
            this.difficulty = quiz.getDifficulty();
            this.quiz_link = quiz.getQuiz_link();
            this.nickname = quiz.getUser().getNickname();
            this.created_At = quiz.getCreatedAt();
        }
    }

    @Getter
    @Setter
    public static class QuizListRespDto {
        private Long id;
        private String title;
        private Integer difficulty;
        private String quiz_link;
        private String nickname;
        private Long study_room_id;

        public QuizListRespDto(Quiz quiz) {
            this.id = quiz.getId();
            this.title = quiz.getTitle();
            this.quiz_link = quiz.getQuiz_link();
            this.nickname = quiz.getUser().getNickname();
            this.study_room_id = quiz.getStudyRoom().getId();
        }
    }
}
