package com.abo2.recode.dto.notice;

import com.abo2.recode.domain.user.User;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
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

    @NotEmpty
    private String createdAt;


    @Builder
    public NoticeRespDto(Long id, String title, String content, String createdBy, String createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }


}
