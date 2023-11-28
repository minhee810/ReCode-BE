package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.quiz.QuizReqDto;
import com.abo2.recode.dto.quiz.QuizRespDto;
import com.abo2.recode.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.function.LongToIntFunction;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class QuizController {

    private final QuizService quizService;

    // 퀴즈 작성
    @PostMapping(value = "/v1/study/{study_room_id}/quiz")
    public ResponseEntity<?> writeQuiz(@AuthenticationPrincipal LoginUser loginUser,
                                       @PathVariable Long study_room_id,
                                       @RequestBody @Valid QuizReqDto.QuizWriteReqDto quizWriteReqDto){
        QuizRespDto.QuizWriteRespDto quizWriteRespDto = quizService.writeQuiz(study_room_id, quizWriteReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "퀴즈 작성이 완료되었습니다.", quizWriteRespDto), HttpStatus.OK);
    }

    // 퀴즈 목록 조회
    @GetMapping(value = "/v1/study/{study_room_id}/quiz-list")
    public ResponseEntity<?> quizList(@AuthenticationPrincipal LoginUser loginUser,
                                      @PathVariable Long study_room_id) {
        List<QuizRespDto.QuizListRespDto> quizRespDtoList = quizService.quizList(study_room_id);

        return new ResponseEntity<>(new ResponseDto<>(1, "퀴즈 목록 불러오기 성공", quizRespDtoList), HttpStatus.OK);
    }

    // 퀴즈 수정
    @PostMapping(value = "/v1/study/{study_room_id}/quiz-modify")
    public ResponseEntity<?> quizModify(@AuthenticationPrincipal LoginUser loginUser,
                                        @PathVariable Long study_room_id,
                                        @RequestBody @Valid QuizReqDto.QuizWriteReqDto quizWriteReqDto){
        QuizRespDto.QuizListRespDto quizzedModify = quizService.quizModify(loginUser.getUser().getId(), study_room_id, quizWriteReqDto);

        return new ResponseEntity<>(new ResponseDto<>(1, "퀴즈 수정 완료", quizzedModify), HttpStatus.OK);
    }

    // 퀴즈 삭제
    @PostMapping(value = "/v1/study/{study_room_id}/quiz/{quiz_id}/delete")
    public ResponseEntity<?> quizDelete(@AuthenticationPrincipal LoginUser loginUser,
                                        @PathVariable Long study_room_id,
                                        @PathVariable Long quiz_id) {
        quizService.quizDelete(loginUser.getUser().getId(), study_room_id, quiz_id);

        return new ResponseEntity<>(new ResponseDto<>(1, "퀴즈 삭제 왼료", null), HttpStatus.OK);
    }
}
