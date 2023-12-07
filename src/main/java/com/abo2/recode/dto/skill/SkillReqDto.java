package com.abo2.recode.dto.skill;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

public class SkillReqDto {

    @Getter
    @Setter
    public static class AdminSkillAddReqDto {
        
        @NotEmpty
        private String skills; //입력한 스킬들

    }
}
