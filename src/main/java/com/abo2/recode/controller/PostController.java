package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.post.PostReqDto;
import com.abo2.recode.dto.post.PostRespDto;
import com.abo2.recode.service.PostService;
import com.abo2.recode.service.StorageService;
import com.abo2.recode.service.StudyService;
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
    private final StudyService studyService;
    private final StorageService storageService;

    // 게시글 목록 조회
    @GetMapping(value = "/v1/study/{studyId}/list")
    public ResponseEntity<?> postList(@AuthenticationPrincipal LoginUser loginUser,
                                      @PathVariable Long studyId,
                                      @RequestParam(value = "keyword", required = false) String keyword,
                                      @RequestParam(value = "category", required = false) Integer category) {
        List<PostRespDto.PostListRespDto> postListRespDto;

        if (keyword != null && !keyword.trim().isEmpty()) {
            postListRespDto = postService.searchList(studyId, keyword);
        } else if (category != null) {
            postListRespDto = postService.filterList(studyId, category);
        } else {
            postListRespDto = postService.postList(studyId);
        }
        return new ResponseEntity<>(new ResponseDto<>(1, "게시글 목록 불러오기 성공", postListRespDto), HttpStatus.OK);
    }


    // 게시글 작성
    @PostMapping("/v1/study/{studyId}/posts")
    public ResponseEntity<?> writePost(@AuthenticationPrincipal LoginUser loginUser,
                                       @RequestBody PostReqDto.PostWriteReqDto postWriteReqDto, @PathVariable Long studyId) {

        PostRespDto.PostWriteRespDto postWriteRespDto = postService.writePost(postWriteReqDto.getUserId(), postWriteReqDto, studyId);

        return new ResponseEntity<>(new ResponseDto<>(1, "글 작성 성공", postWriteRespDto), HttpStatus.OK);
    }

    // 게시글 상세보기
    @GetMapping("/v1/study/{studyId}/post/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId, @PathVariable Long studyId) {
        PostRespDto.PostDetailRespDto postDetailRespDto = postService.getPostById(postId, studyId);

        return new ResponseEntity<>(new ResponseDto<>(1, "게시글 상세보기 완료", postDetailRespDto), HttpStatus.OK);
    }


    // 게시글 수정
    @PutMapping("/v1/study/{studyId}/post/edit/{postId}")
    public ResponseEntity<?> updatePost(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable Long postId, @PathVariable("studyId") Long studyId,
            @RequestBody PostReqDto.PostUpdateReqDto postUpdateReqDto
    ) {
        PostRespDto.PostUpdateRespDto postUpdateRespDto = postService.updatePost(loginUser.getUser().getId(), postId, studyId, postUpdateReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "게시글이 성공적으로 수정되었습니다.", postUpdateRespDto), HttpStatus.OK);
    }


    // 게시글 삭제
    @DeleteMapping("/v1/study/{studyId}/post/{postId}")
    public ResponseEntity<?> deletePost(@AuthenticationPrincipal LoginUser loginUser, @PathVariable Long postId, @PathVariable Long studyId) {
        postService.deletePost(loginUser.getUser().getId(), postId, studyId);

        return new ResponseEntity<>(new ResponseDto<>(1, "게시글이 성공적으로 삭제되었습니다.", null), HttpStatus.OK);
    }


    //관리자 - 스터디 그룹 관리 페이지에서 스터디 멤버 목록 조회
    @GetMapping(value = "/v1/study-member/{studyId}/list")
    public ResponseEntity<?> postStudyMemberListInAdminPage(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable(name = "studyId") Long studyId,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "category", required = false) Integer category) {

        List<PostRespDto.StudyMemberListDto> studyMemberListDtos;

        studyMemberListDtos = studyService.postStudyMemberListInAdminPage(studyId);

        return new ResponseEntity<>(new ResponseDto<>(1,
                "게시글 목록 불러오기 성공", studyMemberListDtos), HttpStatus.OK);
    }

    @DeleteMapping("/v1/study/{studyId}/post/{postId}/{fileName}")
    public ResponseEntity<?> deleteFileAndUpdatePost(@PathVariable Long studyId, @PathVariable Long postId, @PathVariable String fileName) {
        try {
            postService.removeFileNameFromPost(postId);
            return ResponseEntity.ok("파일 삭제 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + e.getMessage());
        }
    }

}