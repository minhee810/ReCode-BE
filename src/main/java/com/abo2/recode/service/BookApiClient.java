package com.abo2.recode.service;

import com.abo2.recode.dto.book.BookRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service
@RequiredArgsConstructor
public class BookApiClient {

    private final RestTemplate restTemplate;

    @Value("${client-id}")
    private String CLIENT_ID;

    @Value("${client-secret}")
    private String CLIENT_SECRET;

    private final String OpenNaverBookUrl_getBooks = "https://openapi.naver.com/v1/search/book.json?query={query}&display=100&total=";

    public BookRespDto requestBook(String query) {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", CLIENT_ID);
        headers.set("X-Naver-Client-Secret", CLIENT_SECRET);

        final HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(OpenNaverBookUrl_getBooks, HttpMethod.GET, entity, BookRespDto.class, query).getBody();
    }
}