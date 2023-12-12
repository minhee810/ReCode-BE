package com.abo2.recode.dto.notice;

import com.abo2.recode.domain.notice.Notice;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NoticeRespDto {

    @NotEmpty
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String title;

    @NotEmpty
    private String content;

    @NotEmpty
    private String createdBy;

    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm")
    private LocalDateTime updatedAt;


    @Builder
    public NoticeRespDto(Long id, String title, String content, String createdBy, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static NoticeRespDto toDto(Notice notice) {
        return NoticeRespDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdBy(notice.getCreatedBy().getNickname())
                .createdAt(notice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt())
                .build();
    }

}
