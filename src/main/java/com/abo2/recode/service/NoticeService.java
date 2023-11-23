package com.abo2.recode.service;

import com.abo2.recode.config.auth.LoginService;
import com.abo2.recode.domain.notice.Notice;
import com.abo2.recode.domain.notice.NoticeRepository;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserRepository;
import com.abo2.recode.dto.notice.NoticeReqDto;

import com.abo2.recode.dto.notice.NoticeRespDto;
import com.abo2.recode.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final LoginService loginService;
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    // 공지사항 작성
    @Transactional
    public NoticeRespDto.AdminAddNoticeRespDto createNotice(NoticeReqDto.AdminAddNoticeReqDto adminAddNoticeReqDto){
        // Notice 엔티티를 생성하고 작성자 정보 설정

        User admin = userRepository.findById(adminAddNoticeReqDto.getUserId().getId())
                .orElseThrow(()->new CustomApiException("존재하지 않는 관리자"));

        Notice notice = Notice.builder()
                .title(adminAddNoticeReqDto.getTitle())
                .content(adminAddNoticeReqDto.getContent())
                .createdBy(admin)
                .build();

        noticeRepository.save(notice);

        NoticeRespDto.AdminAddNoticeRespDto adminAddNoticeRespDto = NoticeRespDto.AdminAddNoticeRespDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .build();

        return adminAddNoticeRespDto;
    }

    // 공지사항 수정
    @Transactional
    public NoticeRespDto.UpdateNoticeRespDto updateNotice(Long noticeId,
                                                          NoticeReqDto.AdminUpdateNoticeReqDto adminUpdateNoticeReqDto) {
        // 공지사항 게시물 조회
        Notice updateNotice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new CustomApiException("존재하지 않는 공지사항 글 입니다. "));

        updateNotice.setNoticeInfo(adminUpdateNoticeReqDto.getContent(), adminUpdateNoticeReqDto.getTitle());

        NoticeRespDto.UpdateNoticeRespDto updateNoticeRespDto = NoticeRespDto.UpdateNoticeRespDto.builder()
                .id(adminUpdateNoticeReqDto.getUserId().getId())
                .title(adminUpdateNoticeReqDto.getTitle())
                .content(adminUpdateNoticeReqDto.getContent())
                .build();

       return updateNoticeRespDto;
    }

    @Transactional
    public void deleteNotice(Long noticeId){
        noticeRepository.deleteById(noticeId);
    }

    // 공지사항 삭제




}
