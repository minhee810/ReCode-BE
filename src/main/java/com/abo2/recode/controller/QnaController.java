package com.abo2.recode.controller;

import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.qna.QnaReqDTO;
import com.abo2.recode.entity.Qna;
import com.abo2.recode.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class QnaController {

    private final QnaService qnaService;

    @PostMapping("/qna")
    public ResponseEntity<?> postQna(@RequestBody Qna qna) {
       QnaReqDTO qnaReqDTO = qnaService.postQna(qna);
        System.out.println(qnaReqDTO.getTitle());
        return new ResponseEntity<>(new ResponseDto<>(1, "Qna 생성 성공", qnaReqDTO), HttpStatus.OK);
    }
}
