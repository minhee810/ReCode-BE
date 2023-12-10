package com.abo2.recode.controller;

import com.abo2.recode.dto.book.BookRespDto;
import com.abo2.recode.service.BookApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookController {

    private final BookApiClient bookApiClient;

    @GetMapping("/book/{query}")
    public BookRespDto goBookPage(@PathVariable("query") String query){
        return bookApiClient.requestBook(query);
    }
}
