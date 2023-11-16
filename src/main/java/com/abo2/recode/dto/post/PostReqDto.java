package com.abo2.recode.dto.post;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class PostReqDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotEmpty
    private String category;

}
