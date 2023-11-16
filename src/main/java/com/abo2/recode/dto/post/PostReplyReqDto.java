package com.abo2.recode.dto.post;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PostReplyReqDto {

    @NotBlank
    private String content;
}
