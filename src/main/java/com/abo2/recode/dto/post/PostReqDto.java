package com.abo2.recode.dto.post;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostReqDto {

    private String title;
    private String content;
    private String category;

}
