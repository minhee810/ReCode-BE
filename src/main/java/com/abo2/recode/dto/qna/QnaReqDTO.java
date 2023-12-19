package com.abo2.recode.dto.qna;

import com.abo2.recode.domain.user.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class QnaReqDTO {

    private Long id;
    private Long userId;
    private String role;
    private String nickname;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public QnaReqDTO(User user,Long id, String title, String content, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        if (user != null) {
            this.userId = user.getId();
            this.role = user.getRole().name();
            this.nickname = user.getNickname();
        } else {
            this.userId = null;
            this.role = "UNKNOWN";
            this.nickname = "탈퇴한 회원 입니다.";
        }
    }
}
