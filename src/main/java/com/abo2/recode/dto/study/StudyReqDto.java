package com.abo2.recode.dto.study;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


public class StudyReqDto {

    @Getter
    @Setter
    public static class StudyCreateReqDto{

        @NotEmpty
        @Size(min = 1, max = 50)
        private String study_name;

        @NotEmpty
        @Size(min = 1, max = 50)
        private String title;

        @NotEmpty
        @Size(min = 1, max = 300)
        private String description;

        @NotEmpty
        private LocalDateTime start_date;

        @NotEmpty
        private LocalDateTime end_date;

        @NotEmpty
        private Integer max_num;

        @NotEmpty
        private String user_id; //별도로 가져오는 코드 필요(입력 받지 않음)

        @NotEmpty
        private LocalDateTime createdAt;

        @NotEmpty
        private LocalDateTime updatedAt;

    }

}
