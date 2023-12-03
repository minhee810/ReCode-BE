package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.post.PostReqDto;
import com.abo2.recode.dto.post.PostRespDto;
import com.abo2.recode.service.PostReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostReplyController {

    private final PostReplyService postReplyService;


    // 게시글 댓글 작성
    @PostMapping("/v1/study/{study_room_id}/post/{post_id}/postReply")
    public ResponseEntity<?> createPostReply(@AuthenticationPrincipal LoginUser loginUser, @PathVariable("post_id") Long postId, @PathVariable("study_room_id") Long studyRoomId, @RequestBody PostReqDto.PostReplyReqDto postReplyReqDto) {
        PostRespDto.PostReplyRespDto createdPostReply = postReplyService.createPostReply(loginUser.getUser().getId(), postId, studyRoomId, postReplyReqDto);

        return new ResponseEntity<>(new ResponseDto<>(1, "댓글 작성 완료", createdPostReply), HttpStatus.OK);
    }

    // 게시글 댓글 상세보기
    @GetMapping("/v1/study/{study_room_id}/post/{post_id}/{postReply_id}")
    public ResponseEntity<?> DetailPostReply(@AuthenticationPrincipal LoginUser loginUser, @PathVariable("study_room_id") Long studyRoomId, @PathVariable("post_id") Long postId, @PathVariable("postReply_id") Long postReplyId) {
        PostRespDto.PostReplyRespDto DetailPostReply = postReplyService.getPostReply(loginUser.getUser().getId(), studyRoomId, postId, postReplyId);

        return new ResponseEntity<>(new ResponseDto<>(1, "댓글 조회 완료", DetailPostReply), HttpStatus.OK);
    }


    // 게시글 댓글 수정
    @PutMapping("/v1/study/{post_id}/postReply/{postReply_id}")
    public ResponseEntity<?> updatePostReply(@AuthenticationPrincipal LoginUser loginUser, @PathVariable("post_id") Long postId, @PathVariable("postReply_id") Long postReplyId, @RequestBody PostReqDto.PostReplyReqDto postReplyReqDto) {
        PostRespDto.PostReplyRespDto updatedPostReply = postReplyService.updatePostReply(loginUser.getUser().getId(), postId, postReplyId, postReplyReqDto);

        return new ResponseEntity<>(new ResponseDto(1, "댓글 수정 완료", updatedPostReply), HttpStatus.OK);
    }

    // 게시글 댓글 삭제
    @DeleteMapping("/v1/study/{post_id}/postReply/{postReply_id}")
    public ResponseEntity<?> deletePostReply(@AuthenticationPrincipal LoginUser loginUser, @PathVariable("post_id") Long postId, @PathVariable("postReply_id") Long postReplyId) {
        postReplyService.deletePostReply(loginUser.getUser().getId(), postId, postReplyId);

        return new ResponseEntity<>(new ResponseDto<>(1, "댓글 삭제 완료", null), HttpStatus.OK);
    }

}

