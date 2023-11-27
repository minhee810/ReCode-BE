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

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class QuizController {

    private final QuizService quizService;

    @PostMapping(value = "/v1/study/{study_room_id}/quiz")
    public ResponseEntity<?> writeQuiz(@AuthenticationPrincipal LoginUser loginUser,
                                       @PathVariable Long study_room_id,
                                       @RequestBody @Valid QuizReqDto.QuizWriteReqDto quizWriteReqDto){
        QuizRespDto.QuizWriteRespDto quizWriteRespDto = quizService.writeQuiz(study_room_id, quizWriteReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "퀴즈 작성이 완료되었습니다.", quizWriteRespDto), HttpStatus.OK);
    }
}
