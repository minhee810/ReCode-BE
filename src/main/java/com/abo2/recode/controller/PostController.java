package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.post.PostReqDto;
import com.abo2.recode.dto.post.PostRespDto;
import com.abo2.recode.service.PostService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class PostController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final PostService postService;

    // 게시글 목록 조회
    @GetMapping(value = "/v1/study/{study_id}/list")
    public ResponseEntity<?> postList(@AuthenticationPrincipal LoginUser loginUser, @PathVariable Long study_id) {
        List<PostRespDto.PostListRespDto> postListRespDto = postService.postList(study_id);
        return new ResponseEntity<>(new ResponseDto<>(1, "게시글 목록 불러오기 성공", postListRespDto), HttpStatus.OK);
    }


    // 게시글 작성
    @PostMapping("/v1/post")
    public ResponseEntity<?> writePost(@RequestBody PostReqDto postReqDto) {
        PostRespDto postRespDto = postService.writePost(postReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "글 작성 성공", postRespDto), HttpStatus.CREATED);
    }
}
