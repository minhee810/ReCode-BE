package com.abo2.recode.dto.notice;

import com.abo2.recode.domain.notice.Notice;
import com.abo2.recode.domain.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class NoticeReqDto {


    @Getter
    @Setter
    public static class AdminAddNoticeReqDto {

        @NotEmpty
        private String id;

        @NotEmpty
        @Size(min = 1, max = 50)
        private String title;

        @NotEmpty
        private String content;

        @NotEmpty
        private User userId;

        @NotEmpty
        @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm")
        private LocalDateTime createdAt;

        @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm")
        private LocalDateTime updatedAt;


        public Notice toEntity() {
            return Notice.builder()
                    .title(title)
                    .content(content)
                    .createdBy(userId)
                    .createdAt(createdAt)
                    .updatedAt(updatedAt)
                    .build();
        }





    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class AdminUpdateNoticeReqDto {

        @NotEmpty
        @Size(min = 1, max = 50)
        private String title;

        @NotEmpty
        private String content;

        @NotEmpty
        private User userId;

        public AdminUpdateNoticeReqDto(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public Notice toEntity() {
            return Notice.builder()
                    .title(title)
                    .content(content)
                    .createdBy(userId)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class GetAllNoticeReqDto {

        @NotEmpty
        private String id;

        @NotEmpty
        @Size(min = 1, max = 50)
        private String title;

        @NotEmpty
        private String content;

        public GetAllNoticeReqDto(String id, String title, String content) {
            this.id = id;
            this.title = title;
            this.content = content;
        }
    }
}
