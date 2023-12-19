package com.abo2.recode.service;

import com.abo2.recode.domain.quiz.Quiz;
import com.abo2.recode.domain.quiz.QuizRepository;
import com.abo2.recode.domain.studyroom.StudyRoom;
import com.abo2.recode.domain.studyroom.StudyRoomRepository;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserRepository;
import com.abo2.recode.dto.quiz.QuizReqDto;
import com.abo2.recode.dto.quiz.QuizRespDto;
import com.abo2.recode.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final StudyRoomRepository studyRoomRepository;

    Logger log = LoggerFactory.getLogger(QuizService.class);

    @Transactional
    public QuizRespDto.QuizWriteRespDto writeQuiz(Long studyId, QuizReqDto.QuizWriteReqDto quizWriteReqDto) {
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
        quiz.setCreatedAt(LocalDate.now());
        quiz.setUpdatedAt(LocalDate.now());

        Quiz saveQuiz = quizRepository.save(quiz);

        return new QuizRespDto.QuizWriteRespDto(saveQuiz);
    }

    @Transactional
    public List<QuizRespDto.QuizListRespDto> quizList(Long studyId) {

        List<Quiz> quizList = quizRepository.findQuizByStudyRoomId(studyId);

        if (quizList.isEmpty()) {
            throw new CustomApiException("스터디에 등록된 퀴즈가 없습니다. 등록해주세요!");
        }

        return quizList.stream()
                .map(QuizRespDto.QuizListRespDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public QuizRespDto.QuizListRespDto quizModify(Long userId, Long studyId, Long quiz_id, QuizReqDto.QuizWriteReqDto quizWriteReqDto) {
        // 유저, 스터디 룸 조회
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomApiException("해당 유저가 존재하지 않습니다."));
        StudyRoom studyRoom = studyRoomRepository.findById(studyId).orElseThrow(() -> new CustomApiException("해당 스터디 룸이 존재하지 않습니다."));

        // 퀴즈 조회 및 작성자 확인
        Quiz quiz = quizRepository.findById(quiz_id).orElseThrow(() -> new CustomApiException("퀴즈가 존재하지 않습니다."));
        if (!quiz.getUser().getId().equals(userId)) {
            throw new CustomApiException("작성자만 수정이 가능합니다.");
        }

        // 퀴즈 수정
        quiz.setTitle(quizWriteReqDto.getTitle());
        quiz.setDifficulty(quizWriteReqDto.getDifficulty());
        quiz.setQuiz_link(quizWriteReqDto.getQuiz_link());
        quiz.setUpdatedAt(LocalDate.now());

        Quiz saveQuiz = quizRepository.save(quiz);
        return new QuizRespDto.QuizListRespDto(saveQuiz);
    }

    @Transactional
    public void quizDelete(Long userId, Long studyId, Long quizId) {
        // 유저, 스터디 룸 조회
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomApiException("해당 유저가 존재하지 않습니다."));
        StudyRoom studyRoom = studyRoomRepository.findById(studyId).orElseThrow(() -> new CustomApiException("해당 스터디 룸이 존재하지 않습니다."));

        // 퀴즈 조회
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new CustomApiException("해당 퀴즈를 찾을 수 없습니다."));

        // 작성자, 스터디 장인지 체크
        boolean isCreator = quiz.getUser().getId().equals(userId);
        boolean isMaster = studyRoom.getMaster().equals("master");

        if (isCreator || isMaster) {
            quizRepository.delete(quiz);
        } else {
            throw new CustomApiException("해당 퀴즈의 작성자와 스터디의 장만이 퀴즈를 삭제할 수 있습니다.");
        }
    }

    @Transactional
    public QuizRespDto.QuizDetailRespDto quizDetail(Long userId, Long studyId, Long quizId) {
        // 유저, 스터디 룸 조회
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomApiException("해당 유저가 존재하지 않습니다."));
        StudyRoom studyRoom = studyRoomRepository.findById(studyId).orElseThrow(() -> new CustomApiException("해당 스터디 룸이 존재하지 않습니다."));

        log.info("요청- userId: {}, studyId: {}, quizId: {}", userId, studyId, quizId);

        // 퀴즈 조회
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new CustomApiException("해당 퀴즈 아이디" + quizId + "를 찾을 수 없습니다."));

        return new QuizRespDto.QuizDetailRespDto(quiz);
    }

    @Transactional
    public List<QuizRespDto.QuizListRespDto> searchList(Long studyId, String keyword) {

        List<Quiz> quizzes = quizRepository.findQuizByKeyword(studyId, keyword);

        if (quizzes.isEmpty()) {
            throw new CustomApiException("해당 퀴즈가 존재하지 않습니다.");
        }

        return quizzes.stream()
                .map((QuizRespDto.QuizListRespDto::new))
                .collect(Collectors.toList());
    }
}