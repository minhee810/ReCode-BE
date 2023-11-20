package com.abo2.recode.dto.skill;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

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
        private ArrayList<String> skills; //skill 테이블 전부 호출
    }
}
