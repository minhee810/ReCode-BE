package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.domain.Qna.Qna;
import com.abo2.recode.domain.user.UserEnum;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.qna.QnaReqDTO;
import com.abo2.recode.dto.qna.QnaResDTO;
import com.abo2.recode.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class QnaController {

    private final QnaService qnaService;

    //Qna 생성
    @PostMapping("/qna")
    public ResponseEntity<?> postQna(@AuthenticationPrincipal LoginUser loginUser, @RequestBody QnaReqDTO qnaReqDTO) {

        try {
            qnaReqDTO.setUser_id(loginUser.getUser().getId());
            qnaService.postQna(qnaReqDTO);
            return new ResponseEntity<>(new ResponseDto<>(1, "Qna 생성 성공", qnaReqDTO), HttpStatus.OK);
        } finally {
            String triggerUrl = "https://c1r67w97gd.execute-api.ap-northeast-2.amazonaws.com/default/post-qna";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForEntity(triggerUrl, null, String.class);
        }
    }

    //Qna 목록 조회
    @GetMapping("/qna")
    public ResponseEntity<?> qnaList(@AuthenticationPrincipal LoginUser loginUser, QnaResDTO qnaResDTO) {
        qnaResDTO.setUser_id(loginUser.getUser().getId());
        List<Qna> qnas = qnaService.qnaList();

        return new ResponseEntity<>(new ResponseDto<>(1, "Qna 목록 조회 성공", qnas), HttpStatus.OK);
    }

    //Qna 단일 조회
    @GetMapping("/qna/{id}")
    public ResponseEntity<?> qna(@AuthenticationPrincipal LoginUser loginUser, @PathVariable Long id, QnaResDTO qnaResDTO) {
        qnaResDTO.setUser_id(loginUser.getUser().getId());

        Qna qna1 = qnaService.qna(id);


        return new ResponseEntity<>(new ResponseDto<>(1, "Qna 단일 조회 성공", qna1), HttpStatus.OK);
    }

    //Qna 수정
    @PutMapping("/qna/{id}")
    public ResponseEntity<?> qnaModify(@AuthenticationPrincipal LoginUser loginUser, @PathVariable Long id, @RequestBody QnaReqDTO qnaReqDTO) {
        qnaReqDTO.setUser_id(loginUser.getUser().getId());
        Qna qna = qnaService.qnaModify(id, qnaReqDTO);

        return new ResponseEntity<>(new ResponseDto<>(1, "Qna 수정 성공", qna), HttpStatus.OK);

    }

    //Qna 식제(관리자 권한)
    @DeleteMapping("/qna/{id}")
    public ResponseEntity<?> qnaDelete(@AuthenticationPrincipal LoginUser loginUser, @PathVariable Long id, QnaReqDTO qnaReqDTO) {
        qnaReqDTO.setUser_id(loginUser.getUser().getId());
        Qna qna = qnaService.qnaDelete(id);
        if (loginUser.getUser().getRole() != UserEnum.ADMIN) {
            return new ResponseEntity<>(new ResponseDto<>(-1, "권한 없음", qna), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ResponseDto<>(1, "Qna 삭제 성공", qna), HttpStatus.OK);
    }


}
