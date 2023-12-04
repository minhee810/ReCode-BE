package com.abo2.recode.dto.skill;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

public class SkillResDto {

    @Getter
    @Setter
    public static class AdminSkillAddResDto {

        @NotEmpty
        private ArrayList<String> skills; //skill 테이블 전부 호출
    }
}
