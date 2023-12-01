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
//        {
//            "code": 1,
//                "msg": "스킬 추가 성공",
//                "data": {
//            "skills": ["devops","flask","django","frontEnd","react","java"] //skill 테이블 전부 호출
//        }
//        }

        @NotEmpty
        private String skills; //skill 테이블 전부 호출

        @NotEmpty
        private String position; // 추가

        @Builder
        public AdminSkillAddResDto(String skills, String position) {
            this.skills = skills;
            this.position = position;
        }
    }

    @Getter
    @Setter
    public static class AdminGetSkillResDto{
//        {
//            "code": 1,
//                "msg": "스킬 추가 성공",
//                "data": {
//            "skills": ["devops","flask","django","frontEnd","react","java"] //skill 테이블 전부 호출
//        }
//        }

        @NotEmpty
        private List<String> skills;

        @Builder
        public AdminGetSkillResDto(List<String> skills) {
            this.skills = skills;

        }
    }
}
