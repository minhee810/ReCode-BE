package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.post.PostDetailRespDto;
import com.abo2.recode.dto.post.PostReqDto;
import com.abo2.recode.dto.post.PostRespDto;
import com.abo2.recode.handler.ex.CustomApiException;
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
    @PostMapping("/v1/study/{study_id}/posts")
    public ResponseEntity<?> writePost(@RequestBody PostReqDto postReqDto, @PathVariable String study_id) {
        PostRespDto postRespDto = postService.writePost(postReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "글 작성 성공", postRespDto), HttpStatus.CREATED);
    }


    // 게시글 상세보기
    @GetMapping("/v1/study/posts/{postId}")
    public ResponseEntity<?> getPostDetail(@PathVariable Long postId) {
        PostDetailRespDto postDetailRespDto = postService.getPostDetail(postId);
        return new ResponseEntity<>(new ResponseDto<>(1, "게시글 상세정보 조회 성공", postDetailRespDto), HttpStatus.OK);
    }


    // 게시글 수정
    @PutMapping("/v1/study/posts/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostReqDto postReqDto) {
        PostRespDto updatePost = postService.updatePost(postId, postReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "게시글 수정 성공", updatePost), HttpStatus.OK);

    }


    // 게시글 삭제
    @DeleteMapping("/v1/study/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {

        try {
            postService.deletePost(postId);
            return new ResponseEntity<>("게시글이 삭제되었습니다.", HttpStatus.OK);

        } catch (CustomApiException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
