package com.abo2.recode.dto.skill;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class SkillResDto {

    @Getter
    @Setter
    public static class AdminSkillAddResDto{

        @NotEmpty
        private String skillName; //skill 테이블 전부 호출

        @NotEmpty
        private String position; // 추가

        @Builder
        public AdminSkillAddResDto(String skillName, String position) {
            this.skillName = skillName;
            this.position = position;
        }
    }

    @Getter
    @Setter
    public static class AdminGetSkillResDto{

        @NotEmpty
        private String skillName;

        @NotEmpty
        private String position;

        @Builder
        public AdminGetSkillResDto(String skillName, String position) {
            this.skillName = skillName;
            this.position = position;

        }
    }

    @Getter
    @Setter
    public static class SkillNameRespDto {

        // 사용자가 선택한 skillName 을 받아오는 dto
        @NotEmpty
        private String skillName;

        public SkillNameRespDto(String skillName) {
            this.skillName = skillName;
        }

    }
}