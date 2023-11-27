package com.abo2.recode.service;

import com.abo2.recode.domain.quiz.Quiz;
import com.abo2.recode.domain.quiz.QuizRepository;
import com.abo2.recode.domain.studymember.StudyMember;
import com.abo2.recode.domain.studymember.StudyMemberRepository;
import com.abo2.recode.domain.studyroom.StudyRoom;
import com.abo2.recode.domain.studyroom.StudyRoomRepository;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserRepository;
import com.abo2.recode.dto.quiz.QuizReqDto;
import com.abo2.recode.dto.quiz.QuizRespDto;
import com.abo2.recode.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final StudyRoomRepository studyRoomRepository;

    @Transactional
    public QuizRespDto.QuizWriteRespDto writeQuiz(Long study_room_id, QuizReqDto.QuizWriteReqDto quizWriteReqDto){
        // 1. user 아이디 조회
        User user = userRepository.findById(quizWriteReqDto.getUserId()).orElseThrow(() -> new CustomApiException("존재하지 않는 사용자입니다."));

        // 2. StudyRoom 아이디 조회
        StudyRoom studyRoom = studyRoomRepository.findById(quizWriteReqDto.getStudyRoomId()).orElseThrow(() -> new CustomApiException("스터디 룸이 존재하지 않습니다."));

        // dto 에 반영
        Quiz quiz = new Quiz();
        quiz.setTitle(quizWriteReqDto.getTitle());
        quiz.setDifficulty(quizWriteReqDto.getDifficulty());
        quiz.setQuiz_link(quizWriteReqDto.getQuiz_link());
        quiz.setUser(user);
        quiz.setStudyRoom(studyRoom);

        Quiz saveQuiz = quizRepository.save(quiz);

        return new QuizRespDto.QuizWriteRespDto(saveQuiz);
    }
}
