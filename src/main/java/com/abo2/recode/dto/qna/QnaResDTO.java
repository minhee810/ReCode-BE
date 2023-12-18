package com.abo2.recode.dto.qna;


import com.abo2.recode.domain.qna.QnaReply;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class QnaResDTO {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private String role;
    private String nickname;
    private List<QnaReplyDTO> qnaReplyList;

    @Builder
    public QnaResDTO(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, User user, List<QnaReplyDTO> qnaReplyList) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        if (user != null) {
            this.userId = user.getId();
            this.role = user.getRole().name();
            this.nickname = user.getNickname();
        } else {
            this.userId = null;
            this.role = "UNKNOWN";
            this.nickname = "탈퇴한 회원 입니다.";
        }
        this.qnaReplyList = qnaReplyList;
    }
}
