package com.abo2.recode.dto.quiz;

import com.abo2.recode.domain.quiz.Quiz;
import lombok.Getter;
import lombok.Setter;

public class QuizRespDto {

    @Getter
    @Setter
    public static class QuizWriteRespDto {
        private Long id;
        private String title;
        private Integer difficulty;
        private String quiz_link;
        private String nickname;

        public QuizWriteRespDto(Quiz quiz) {
            this.id = quiz.getId();
            this.title = quiz.getTitle();
            this.difficulty = quiz.getDifficulty();
            this.quiz_link = quiz.getQuiz_link();
            this.nickname = quiz.getUser().getNickname();
        }
    }
}
