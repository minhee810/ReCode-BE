package com.abo2.recode.controller;

import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.post.PostReqDto;
import com.abo2.recode.dto.post.PostRespDto;
import com.abo2.recode.service.PostReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostReplyController {

    private final PostReplyService postReplyService;


    // 게시글 댓글 작성
    @PostMapping("/v1/study/{postId}/comments")
    public ResponseEntity<?> createPostReply(@RequestBody PostReqDto.PostReplyReqDto postReplyReqDto) {
        PostRespDto.PostReplyRespDto createdPostReply = postReplyService.createPostReply(postReplyReqDto);

        return new ResponseEntity<>(new ResponseDto<>(1, "댓글 작성 완료", postReplyReqDto), HttpStatus.OK);
    }
}

