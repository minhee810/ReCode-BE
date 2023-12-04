package com.abo2.recode.dto.admin;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

public class AdminReqDto {

    @Getter
    @Setter
    public static class MemberRoleReqDto{

    /* {
            "userId" : 1,
            "role": "group_leader" //or "group_member",
            "studyId" : 1
        }*/

        @NotEmpty
        private String role;

    }

}
