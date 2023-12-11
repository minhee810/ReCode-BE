package com.abo2.recode.dto.badge;

import com.abo2.recode.domain.estimate.Estimate;
import lombok.Getter;
import lombok.Setter;

public class BadgeRespDto {

    @Getter
    @Setter
    public static class EstimateRespDto{
        private Integer point;

        public EstimateRespDto(Estimate estimate){
            this.point = estimate.getPoint();
        }
    }
}
