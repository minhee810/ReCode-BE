package com.abo2.recode.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class BookResultDto {

    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<BookRespDto> items;
}
