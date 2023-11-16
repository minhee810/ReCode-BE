package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.qna.QnaReqDTO;
import com.abo2.recode.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class QnaController {

    private final QnaService qnaService;

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
}
