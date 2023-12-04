package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.domain.qna.Qna;
import com.abo2.recode.domain.qna.QnaRepository;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserEnum;
import com.abo2.recode.domain.user.UserRepository;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.qna.QnaReqDTO;
import com.abo2.recode.dto.qna.QnaResDTO;
import com.abo2.recode.handler.ex.CustomApiException;
import com.abo2.recode.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class QnaController {

    private final QnaService qnaService;
    private final QnaRepository qnaRepository;
    private final UserRepository userRepository;

    //Qna 생성
    @PostMapping("/v1/qna")
    public ResponseEntity<?> postQna(@AuthenticationPrincipal LoginUser loginUser, @RequestBody QnaReqDTO qnaReqDTO) {

        try {
            qnaReqDTO.setUserId(loginUser.getUser().getId());

            qnaService.postQna(qnaReqDTO);

//            //슬랙봇 호출
//            String triggerUrl = "https://c1r67w97gd.execute-api.ap-northeast-2.amazonaws.com/default/post-qna";
//            RestTemplate restTemplate = new RestTemplate();
//            restTemplate.postForEntity(triggerUrl, null, String.class);

            return new ResponseEntity<>(new ResponseDto<>(1, "Qna 생성 성공", qnaReqDTO), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto<>(-1, " Qna 생성 실패", qnaReqDTO), HttpStatus.BAD_REQUEST);
        }
    }

    //Qna 목록 조회
    @GetMapping("/v1/qna")
    public ResponseEntity<?> qnaList(@AuthenticationPrincipal LoginUser loginUser, QnaResDTO qnaResDTO) {
        qnaResDTO.setUserId(loginUser.getUser().getId());

        List<Qna> qnas = qnaService.qnaList();

        return new ResponseEntity<>(new ResponseDto<>(1, "Qna 목록 조회 성공", qnas), HttpStatus.OK);
    }

    //Qna 단일 조회
    @GetMapping("/v1/qna/{id}")
    public ResponseEntity<?> qna(@AuthenticationPrincipal LoginUser loginUser, @PathVariable Long id, QnaResDTO qnaResDTO) {
        qnaResDTO.setUserId(loginUser.getUser().getId());

        Qna qna1 = qnaService.qna(id);

        return new ResponseEntity<>(new ResponseDto<>(1, "Qna 단일 조회 성공", qna1), HttpStatus.OK);
    }

    //Qna 수정
    @PutMapping("/v1/qna/{id}")
    public ResponseEntity<?> qnaModify(@AuthenticationPrincipal LoginUser loginUser, @PathVariable Long id, @RequestBody QnaReqDTO qnaReqDTO) {

        qnaReqDTO.setUserId(loginUser.getUser().getId());

        User user = userRepository.findById(loginUser.getUser().getId()).orElseThrow(
                () -> new CustomApiException("User가 존재하지 않습니다!")
        );
        Qna qnaInfo = qnaRepository.findById(id).orElseThrow(
                () -> new CustomApiException("Qna가 존재하지 않습니다!")
        );


        if (qnaInfo.getUserId().getId() != user.getId()) {
            return new ResponseEntity<>(new ResponseDto<>(-1, " 권한 없음", null), HttpStatus.FORBIDDEN);
        }
        else {
            qnaService.qnaModify(id, qnaReqDTO);

            return new ResponseEntity<>(new ResponseDto<>(1, "Qna 수정 성공", qnaReqDTO), HttpStatus.OK);
        }
    } //qnaModify()


    //Qna 삭제 (관리자 권한)
    @Secured(value = "ROLE_ADMIN")
    @DeleteMapping("/admin/v1/qna/{qnaId}")
    public ResponseEntity<?> qnaDelete(
            @PathVariable(value = "qnaId") Long qnaId
    ) {

        Qna qnaInfo = qnaRepository.findById(qnaId).orElseThrow(
                () -> new CustomApiException("Qna가 존재하지 않습니다!")
        );

        qnaService.qnaDelete(qnaId);

        QnaResDTO qnaResDTO = QnaResDTO.builder()
                .userId(qnaInfo.getUserId().getId())
                .title(qnaInfo.getTitle())
                .category(qnaInfo.getCategory())
                .content(qnaInfo.getContent())
                .build();

        return new ResponseEntity<>(new ResponseDto<>(1, "Qna 삭제 성공", qnaResDTO), HttpStatus.OK);
    }
}
