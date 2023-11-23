package com.abo2.recode.dto.notice;

import com.abo2.recode.domain.notice.Notice;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.dto.user.UserRespDto;
import lombok.*;
import org.hibernate.sql.Update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.security.cert.CertPathBuilder;
import java.time.LocalDateTime;

public class NoticeRespDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class AdminAddNoticeRespDto{

        @NotEmpty
        private Long id;

        @NotEmpty
        @Size(min = 1, max = 50)
        private String title;

        @NotEmpty
        private String content;

        @Builder
        public AdminAddNoticeRespDto(Long id, String title, String content) {
            this.id = id;
            this.title = title;
            this.content = content;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdateNoticeRespDto{

        @NotEmpty
        private Long id;

        @NotEmpty
        @Size(min = 1, max = 50)
        private String title;

        @NotEmpty
        private String content;

        @Builder
        public UpdateNoticeRespDto(Long id, String title, String content) {
            this.id = id;
            this.title = title;
            this.content = content;
        }
    }
}
