package com.abo2.recode.dto.quiz;

import com.abo2.recode.domain.quiz.Quiz;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class QuizRespDto {

    @Getter
    @Setter
    public static class QuizWriteRespDto {
        private Long id;
        private String title;
        private Integer difficulty;
        private String quiz_link;
        private String nickname;
        private LocalDate createdAt;
        private LocalDate updatedAt;


        public QuizWriteRespDto(Quiz quiz) {
            this.id = quiz.getId();
            this.title = quiz.getTitle();
            this.difficulty = quiz.getDifficulty();
            this.quiz_link = quiz.getQuiz_link();
            this.nickname = quiz.getUser().getNickname();
            this.createdAt = quiz.getCreatedAt();
            this.updatedAt = quiz.getUpdatedAt();
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
        private Long studyId;
        private LocalDate updatedAt;

        public QuizListRespDto(Quiz quiz) {
            this.id = quiz.getId();
            this.title = quiz.getTitle();
            this.difficulty = quiz.getDifficulty();
            this.quiz_link = quiz.getQuiz_link();
            this.nickname = quiz.getUser().getNickname();
            this.studyId = quiz.getStudyRoom().getId();
            this.updatedAt = quiz.getUpdatedAt();
        }
    }

    @Getter
    @Setter
    public static class QuizDetailRespDto {
        private Long id;
        private String title;
        private String quiz_link;
        private String nickname;
        private Integer difficulty;

        public QuizDetailRespDto(Quiz quiz) {
            this.id = quiz.getId();
            this.title = quiz.getTitle();
            this.difficulty = quiz.getDifficulty();
            this.quiz_link = quiz.getQuiz_link();
            this.nickname = quiz.getUser().getNickname();
        }
    }
}
