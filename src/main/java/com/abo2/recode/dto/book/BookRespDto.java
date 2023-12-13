package com.abo2.recode.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookRespDto {

    private Integer display;
    private Integer total;
    private Item[] items;

    @Getter
    @Setter
    public static class Item {
        private String image;
        private String title;
        private String link;
        private String author;
        private String publisher;
        private String pubdate;
        private String description;
    }
}