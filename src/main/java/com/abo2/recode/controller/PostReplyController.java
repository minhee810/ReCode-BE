package com.abo2.recode.controller;

import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.post.PostReplyReqDto;
import com.abo2.recode.dto.post.PostReplyRespDto;
import com.abo2.recode.service.PostReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/postreply")
public class PostReplyController {

    private final PostReplyService postReplyService;

    @PostMapping
    public ResponseEntity<?> postReply(@Valid @RequestBody PostReplyReqDto postReplyReqDto) {
        PostReplyRespDto postReplyRespDto = postReplyService.postReply(postReplyReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "댓글 작성 성공", postReplyRespDto), HttpStatus.CREATED);

    }


}
