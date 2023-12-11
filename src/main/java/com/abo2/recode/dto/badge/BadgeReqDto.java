package com.abo2.recode.dto.badge;

import lombok.Getter;
import lombok.Setter;

public class BadgeReqDto {

    @Getter
    @Setter
    public static class EstimateReqDto {
        private Integer point;
        private Long studyId;
        private Long userId;
    }

}
