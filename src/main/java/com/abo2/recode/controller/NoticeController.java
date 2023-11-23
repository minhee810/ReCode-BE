package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.domain.notice.Notice;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.notice.NoticeReqDto;

import com.abo2.recode.dto.notice.NoticeRespDto;
import com.abo2.recode.dto.user.UserReqDto;
import com.abo2.recode.dto.user.UserRespDto;
import com.abo2.recode.handler.ex.CustomApiException;
import com.abo2.recode.service.NoticeService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/vi")
public class NoticeController {

    private final NoticeService noticeService;


    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/notice")
    public ResponseEntity<?> createNotice(@AuthenticationPrincipal @Valid LoginUser loginUser,
                                          @RequestBody NoticeReqDto.AdminAddNoticeReqDto adminAddNoticeReqDto){

        adminAddNoticeReqDto.setUserId(loginUser.getUser());
        NoticeRespDto.AdminAddNoticeRespDto adminAddNoticeRespDto = noticeService.createNotice(adminAddNoticeReqDto);

        return new ResponseEntity<>(new ResponseDto<>(1, "관리자 공지사항 작성 성공", adminAddNoticeRespDto), HttpStatus.OK);
    }


    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/notice/{notice_id}")
    public ResponseEntity<?> updateNotice(@AuthenticationPrincipal @Valid LoginUser loginUser,
                                          @RequestBody NoticeReqDto.AdminUpdateNoticeReqDto adminUpdateNoticeReqDto,
                                          @PathVariable("notice_id") Long noticeId){

        adminUpdateNoticeReqDto.setUserId(loginUser.getUser());
        NoticeRespDto.UpdateNoticeRespDto updateNoticeRespDto = noticeService.updateNotice(noticeId , adminUpdateNoticeReqDto);

        return new ResponseEntity<>(new ResponseDto<>(1, "공지사항 수정 성공", updateNoticeRespDto), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/notice/{notice_id}")
    public ResponseEntity<?> deleteNotice(@AuthenticationPrincipal @Valid LoginUser loginUser,
                                          @PathVariable("notice_id") Long noticeId){
        noticeService.deleteNotice(noticeId);
        return new ResponseEntity<>(new ResponseDto<>(1, "공지사항 삭제 성공", null), HttpStatus.OK);
    }

}
