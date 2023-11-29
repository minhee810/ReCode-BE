package com.abo2.recode.dto.admin;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

public class AdminReqDto {

    @Getter
    @Setter
    public static class MemberRoleReqDto{

    /* {
            "user_id" : 1,
            "role": "group_leader" //or "group_member",
            "study_id" : 1
        }*/

        @NotEmpty
        private Long user_id;

        @NotEmpty
        private Long study_id;

        @NotEmpty
        private String role;

    }

}
