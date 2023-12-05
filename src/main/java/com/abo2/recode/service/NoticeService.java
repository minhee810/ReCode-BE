package com.abo2.recode.service;

import com.abo2.recode.domain.notice.Notice;
import com.abo2.recode.domain.notice.NoticeRepository;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserRepository;
import com.abo2.recode.dto.notice.NoticeReqDto;
import com.abo2.recode.dto.notice.NoticeRespDto;
import com.abo2.recode.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private static final Logger logger = LoggerFactory.getLogger(NoticeService.class);
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    // 공지사항 작성
    @Transactional
    public NoticeRespDto createNotice(NoticeReqDto.AdminAddNoticeReqDto adminAddNoticeReqDto) {
        // Notice 엔티티를 생성하고 작성자 정보 설정

        User admin = userRepository.findById(adminAddNoticeReqDto.getUserId().getId())
                .orElseThrow(() -> new CustomApiException("존재하지 않는 관리자"));

        Notice notice = Notice.builder()
                .title(adminAddNoticeReqDto.getTitle())
                .content(adminAddNoticeReqDto.getContent())
                .createdBy(admin)
                .build();

        noticeRepository.save(notice);

        NoticeRespDto adminAddNoticeRespDto = NoticeRespDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .build();

        return adminAddNoticeRespDto;
    }

    // 공지사항 수정
    @Transactional
    public NoticeRespDto updateNotice(Long noticeId, NoticeReqDto.AdminUpdateNoticeReqDto adminUpdateNoticeReqDto) {
        // 공지사항 게시물 조회
        Notice updateNotice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new CustomApiException("존재하지 않는 공지사항 글 입니다. "));

        updateNotice.setNoticeInfo(adminUpdateNoticeReqDto.getTitle(), adminUpdateNoticeReqDto.getContent());

        NoticeRespDto updateNoticeRespDto = NoticeRespDto.builder()
                .id(adminUpdateNoticeReqDto.getUserId().getId())
                .title(adminUpdateNoticeReqDto.getTitle())
                .content(adminUpdateNoticeReqDto.getContent())
                .build();

        return updateNoticeRespDto;
    }

    // 공지사항 상세보기
    @Transactional
    public NoticeRespDto detailNotice(Long noticeId) {

        Optional<Notice> detailNotice = noticeRepository.findById(noticeId); // null 값 체크
        if (detailNotice.isPresent()) {
            return convertToDto(detailNotice.get());        // 값이 존재할 때만 dto 형태로 반환
        } else {
            throw new CustomApiException("존재하지 않는 공지사항 글입니다. ");
        }
    }

    // 공지사항 삭제
    @Transactional
    public void deleteNotice(Long noticeId) {
        noticeRepository.deleteById(noticeId);
    }


    // 공지사항 목록 조회
    @Transactional
    public List<NoticeRespDto> getAllNotices() {
        List<Notice> notice = noticeRepository.findAll();
        return notice.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private NoticeRespDto convertToDto(Notice notice) {
        NoticeRespDto dto = new NoticeRespDto();
        dto.setId(notice.getId());
        dto.setTitle(notice.getTitle());
        dto.setContent(notice.getContent());
        dto.setCreatedBy(notice.getCreatedBy().getUsername());  // User 타입 반환해야함.

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm");
        String formatterCreatedAt = notice.getCreatedAt().format(formatter);
        dto.setCreatedAt(formatterCreatedAt);  // LocalDateTime 반환해야함.

        return dto;
    }

}
