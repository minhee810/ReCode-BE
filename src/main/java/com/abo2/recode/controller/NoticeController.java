package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;

import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.notice.NoticeReqDto;

import com.abo2.recode.dto.notice.NoticeRespDto;

import com.abo2.recode.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NoticeController {

    private final NoticeService noticeService;


    // 공지사항 작성
    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/admin/v1/notice")
    public ResponseEntity<?> createNotice(@AuthenticationPrincipal @Valid LoginUser loginUser,
                                          @RequestBody NoticeReqDto.AdminAddNoticeReqDto adminAddNoticeReqDto) {

        adminAddNoticeReqDto.setUserId(loginUser.getUser());
        NoticeRespDto adminAddNoticeRespDto = noticeService.createNotice(adminAddNoticeReqDto);

        return new ResponseEntity<>(new ResponseDto<>(1, "관리자 공지사항 작성 성공", adminAddNoticeRespDto), HttpStatus.OK);
    }


    // 공지사항 수정
    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/admin/v1/notice/{notice_id}")
    public ResponseEntity<?> updateNotice(@AuthenticationPrincipal @Valid LoginUser loginUser,
                                          @RequestBody NoticeReqDto.AdminUpdateNoticeReqDto adminUpdateNoticeReqDto,
                                          @PathVariable("notice_id") Long noticeId) {

        adminUpdateNoticeReqDto.setUserId(loginUser.getUser());
        NoticeRespDto updateNoticeRespDto = noticeService.updateNotice(noticeId, adminUpdateNoticeReqDto);

        return new ResponseEntity<>(new ResponseDto<>(1, "공지사항 수정 성공", updateNoticeRespDto), HttpStatus.OK);
    }


    // 공지사항 삭제
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/admin/v1/notice/{notice_id}")
    public ResponseEntity<?> deleteNotice(@AuthenticationPrincipal @Valid LoginUser loginUser,
                                          @PathVariable("notice_id") Long noticeId) {
        noticeService.deleteNotice(noticeId);
        return new ResponseEntity<>(new ResponseDto<>(1, "공지사항 삭제 성공", null), HttpStatus.OK);
    }


    // 공지사항 목록 조회 - custom 조회 가능
    @GetMapping("/v1/notice-list")
    public ResponseEntity<?> getAllNotices() {
        List<NoticeRespDto> getAllNoticeRespDto = noticeService.getAllNotices();
        return new ResponseEntity<>(new ResponseDto<>(1, "공지사항 목록 조회 성공", getAllNoticeRespDto), HttpStatus.OK);
    }

    // 공지사항 상세보기
    @GetMapping("/v1/notice-detail/{notice_id}")
    public ResponseEntity<?> detailNotice(@Valid @PathVariable("notice_id") Long noticeId) {

        NoticeRespDto detailNoticeDto = noticeService.detailNotice(noticeId);
        return new ResponseEntity<>(new ResponseDto<>(1, "공지사항 상세보기 성공", detailNoticeDto), HttpStatus.OK);

    }

}
