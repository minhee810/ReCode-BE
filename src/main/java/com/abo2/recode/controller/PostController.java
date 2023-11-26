package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.post.PostRespDto;
import com.abo2.recode.service.PostService;
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

    // 게시글 목록 조회
    @GetMapping(value = "/v1/study/{study_id}/list")
    public ResponseEntity<?> postList(@AuthenticationPrincipal LoginUser loginUser,
                                      @PathVariable Long study_id,
                                      @RequestParam(value = "keyword", required = false) String keyword,
                                      @RequestParam(value = "category", required = false) Integer category){
        List<PostRespDto.PostListRespDto> postListRespDto;

        if (keyword != null && !keyword.trim().isEmpty()) {
            postListRespDto = postService.searchList(study_id, keyword);
        } else if (category != null) {
            postListRespDto = postService.filterList(study_id, category);
        } else {
            postListRespDto = postService.postList(study_id);
        }
        return new ResponseEntity<>(new ResponseDto<>(1, "게시글 목록 불러오기 성공", postListRespDto), HttpStatus.OK);
    }//postList()

    //관리자 - 스터디 그룹 관리 페이지에서 스터디 멤버 목록 조회
    @GetMapping(value = "/v1/study-member/{study_id}/list")
    public ResponseEntity<?> postStudyMemberListInAdminPage(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable(name = "study_id") Long study_id,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "category", required = false) Integer category){

        List<PostRespDto.StudyMemberListDto> studyMemberListDtos;

        studyMemberListDtos = studyService.postStudyMemberListInAdminPage(study_id);

        return new ResponseEntity<>(new ResponseDto<>(1,
                "게시글 목록 불러오기 성공", studyMemberListDtos), HttpStatus.OK);
    }//postStudyMemberListInAdminPage()



}
