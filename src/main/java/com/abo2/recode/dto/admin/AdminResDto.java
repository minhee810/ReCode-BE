package com.abo2.recode.dto.admin;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

public class AdminResDto {

    @Getter
    @Setter
    public static class StudyDeleteResponseDto{
//        {
//            "code": 1
//            "msg": "스터디 모집글이 성공적으로 삭제되었습니다.",
//                "data": {
//            "id": "12345"
//        }
//        }

        @NotEmpty
        private Long study_id;

    }
}
