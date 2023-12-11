package com.abo2.recode.dto.badge;

import com.abo2.recode.domain.badge.Badge;
import com.abo2.recode.domain.badge.UserBadge;
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

    @Getter
    @Setter
    public static class GetBadgeRespDto {
        private String nickname;
        private String name;
        private Integer point;

        public GetBadgeRespDto(UserBadge userBadge) {
            this.nickname = userBadge.getUser().getNickname();
            this.name = userBadge.getBadge().getName();
            this.point = userBadge.getPoint();
        }
    }
}
