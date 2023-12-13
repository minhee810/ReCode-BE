package com.abo2.recode.dto.skill;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class SkillReqDto {

    @Getter
    @Setter
    public static class AdminSkillAddReqDto {

        @NotEmpty
        private String skillName; //입력한 스킬들

        @NotEmpty
        private String position;


        // 생성자
        public AdminSkillAddReqDto(String skillName, String position) {
            this.skillName = skillName;
            this.position = position;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class GetSkillNameReqDto {

        // 사용자가 선택한 skillName 을 받아오는 dto
        @NotEmpty
        private List<String> skillName;
    }

    // 사용자가 선택한 skill
    @Getter
    @Setter
    public class PositionDto {
        @NotEmpty
        private String selectedPosition;

        public PositionDto(String selectedPosition) {
            this.selectedPosition = selectedPosition;
        }
    }
}