package com.abo2.recode.dto.qna;


import com.abo2.recode.domain.user.UserEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
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
    public QnaResDTO(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, Long userId, UserEnum role, String nickname, List<QnaReplyDTO> qnaReplyList) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.role = role.name();
        this.nickname = nickname;
        this.qnaReplyList = qnaReplyList;
    }


    public QnaResDTO(Long id) {
        this.id = id;
    }
}
