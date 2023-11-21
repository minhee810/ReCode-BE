package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.domain.studyroom.StudyRoom;
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
    @GetMapping(value = "/v1/study/{study_room_id}/list")
    public ResponseEntity<?> postList(@AuthenticationPrincipal LoginUser loginUser, @PathVariable("study_room_id") Long studyRoomId) {
        List<PostRespDto.PostListRespDto> postListRespDto = postService.postList(studyRoomId);
        return new ResponseEntity<>(new ResponseDto<>(1, "게시글 목록 불러오기 성공", postListRespDto), HttpStatus.OK);
    }


    // 게시글 작성
    @PostMapping("/v1/study/{study_room_id}/posts")
    public ResponseEntity<?> writePost(@AuthenticationPrincipal LoginUser loginUser,
                                       @RequestBody PostReqDto.PostWriteReqDto postWriteReqDto, @PathVariable("study_room_id") Long studyRoomId) {

        postService.writePost(loginUser.getUser().getId(), postWriteReqDto, studyRoomId);

        return new ResponseEntity<>(new ResponseDto<>(1, "글 작성 성공", null), HttpStatus.OK);
    }

//    // 게시글 상세보기
//    @GetMapping("/v1/study/posts/{post_id}")
//    public ResponseEntity<PostRespDto> getPostById(@PathVariable Long post_id) {
//        PostRespDto postRespDto = postService.getPostById(post_id);
//        return new ResponseEntity<>(postRespDto, HttpStatus.OK);
//    }
//
//
//    // 게시글 수정
//    @PutMapping("/v1/study/posts/{post_id}")
//    public ResponseEntity<PostRespDto> updatePost(
//            @PathVariable Long post_id,
//            @RequestBody PostReqDto postReqDto
//    ) {
//        PostRespDto responseDto = postService.updatePost(post_id, postReqDto);
//        return new ResponseEntity<>(responseDto, HttpStatus.OK);
//    }


    // 게시글 삭제
    @DeleteMapping("/v1/study/posts/{post_id}")
    public ResponseEntity<String> deletePost(@PathVariable Long post_id) {
        postService.deletePost(post_id);
        return new ResponseEntity<>("게시글이 성공적으로 삭제되었습니다.", HttpStatus.OK);
    }

}
