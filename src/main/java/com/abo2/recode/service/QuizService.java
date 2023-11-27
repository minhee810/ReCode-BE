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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        quiz.setCreatedAt(LocalDateTime.now());
        quiz.setUpdatedAt(LocalDateTime.now());

        Quiz saveQuiz = quizRepository.save(quiz);

        return new QuizRespDto.QuizWriteRespDto(saveQuiz);
    }

    @Transactional
    public List<QuizRespDto.QuizListRespDto> quizList(Long study_room_id) {

        List<Quiz> quizList = quizRepository.findQuizByStudyRoomId(study_room_id);

        if (quizList.isEmpty()) {
            throw new CustomApiException("스터디에 등록된 퀴즈가 없습니다. 등록해주세요!");
        }

        return quizList.stream()
                .map(QuizRespDto.QuizListRespDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public QuizRespDto.QuizWriteRespDto quizModify(Long userId, Long study_room_id, QuizRespDto.QuizWriteRespDto quizWriteRespDto) {
        // 1. 유저 조회
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomApiException("해당 유저가 존재하지 않습니다."));

        // 2. 스터디 룸 조회
        StudyRoom studyRoom = studyRoomRepository.findById(study_room_id).orElseThrow(() -> new CustomApiException("해당 스터디 룸이 존재하지 않습니다."));

        // 3. 해당 유저가 작성한 글이 아닐 경우 수정하지 못하도록
        QuizReqDto.QuizWriteReqDto quizWriteReqDto = null;
        Quiz saveQuiz;
        if (userId != quizWriteReqDto.getUserId()) {
            throw new CustomApiException("작성자만 수정이 가능합니다.");
        } else {
            Quiz quiz = new Quiz();
            quiz.setTitle(quizWriteReqDto.getTitle());
            quiz.setDifficulty(quizWriteReqDto.getDifficulty());
            quiz.setQuiz_link(quizWriteReqDto.getQuiz_link());
            quiz.setUser(user);
            quiz.setStudyRoom(studyRoom);

            saveQuiz = quizRepository.save(quiz);
        }
        return new QuizRespDto.QuizWriteRespDto(saveQuiz);
    }
}
