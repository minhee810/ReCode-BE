package com.abo2.recode.dto.admin;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

public class AdminReqDto {

    @Getter
    @Setter
    public static class MemberRoleReqDto {

        @NotEmpty
        private String role;

    }

}
