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

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class QuizController {

    private final QuizService quizService;

    // 퀴즈 작성
    @PostMapping(value = "/v1/study/{studyId}/quiz")
    public ResponseEntity<?> writeQuiz(@AuthenticationPrincipal LoginUser loginUser,
                                       @PathVariable Long studyId,
                                       @RequestBody @Valid QuizReqDto.QuizWriteReqDto quizWriteReqDto) {
        QuizRespDto.QuizWriteRespDto quizWriteRespDto = quizService.writeQuiz(studyId, quizWriteReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "퀴즈 작성이 완료되었습니다.", quizWriteRespDto), HttpStatus.OK);
    }

    // 퀴즈 목록 조회
    @GetMapping(value = "/v1/study/{studyId}/quiz-list")
    public ResponseEntity<?> quizList(@AuthenticationPrincipal LoginUser loginUser,
                                      @PathVariable Long studyId,
                                      @RequestParam(value = "keyword", required = false) String keyword) {
        List<QuizRespDto.QuizListRespDto> quizListRespDto;

        if (keyword != null && !keyword.trim().isEmpty()) {
            quizListRespDto = quizService.searchList(studyId, keyword);
        } else {
            quizListRespDto = quizService.quizList(studyId);
        }

        return new ResponseEntity<>(new ResponseDto<>(1, "퀴즈 목록 불러오기 성공", quizListRespDto), HttpStatus.OK);
    }

    // 퀴즈 수정
    @PostMapping(value = "/v1/study/{studyId}/quiz/{quiz_id}/quiz-modify")
    public ResponseEntity<?> quizModify(@AuthenticationPrincipal LoginUser loginUser,
                                        @PathVariable Long studyId,
                                        @PathVariable Long quiz_id,
                                        @RequestBody @Valid QuizReqDto.QuizWriteReqDto quizWriteReqDto){
        QuizRespDto.QuizListRespDto quizzedModify = quizService.quizModify(loginUser.getUser().getId(), studyId, quiz_id, quizWriteReqDto);

        return new ResponseEntity<>(new ResponseDto<>(1, "퀴즈 수정 완료", quizzedModify), HttpStatus.OK);
    }

    // 퀴즈 삭제
    @PostMapping(value = "/v1/study/{studyId}/quiz/{quiz_id}/delete")
    public ResponseEntity<?> quizDelete(@AuthenticationPrincipal LoginUser loginUser,
                                        @PathVariable Long studyId,
                                        @PathVariable Long quiz_id) {
        quizService.quizDelete(loginUser.getUser().getId(), studyId, quiz_id);

        return new ResponseEntity<>(new ResponseDto<>(1, "퀴즈 삭제 왼료", null), HttpStatus.OK);
    }

    // 퀴즈 상세보기
    @GetMapping(value = "/v1/study/{studyId}/quiz/{quiz_id}/detail")
    public ResponseEntity<?> quizDetail(@AuthenticationPrincipal LoginUser loginUser,
                                        @PathVariable Long studyId,
                                        @PathVariable Long quiz_id) {
        QuizRespDto.QuizDetailRespDto quizDetailRespDto = quizService.quizDetail(loginUser.getUser().getId(), studyId, quiz_id);

        return new ResponseEntity<>(new ResponseDto<>(1, "퀴즈 상세보기", quizDetailRespDto), HttpStatus.OK);
    }
}