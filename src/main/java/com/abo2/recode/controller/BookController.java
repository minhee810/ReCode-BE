package com.abo2.recode.controller;

import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.dto.book.BookRespDto;
import com.abo2.recode.dto.book.BookResultDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class BookController {

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String clientSecret;

    @GetMapping(value = "/book-list")
    public ResponseEntity<?> bookList(String text){

        // String apiURL = "https://openapi.naver.com/v1/search/book.json?query=" + text
        URI uri = UriComponentsBuilder
                .fromUriString("http://openapi.naver.com")
                .path("/v1/search/book.json")
                .queryParam("query", text)
                .queryParam("display", 10)
                .queryParam("start", 1)
                .queryParam("sort", "sim")
                .encode()
                .build()
                .toUri();

        // 요청
        RequestEntity<?> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Seceret", clientSecret)
                .build();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);

        // JSON Parse (JSON 문자열을 객체로 문서화)
        ObjectMapper om = new ObjectMapper();
        BookResultDto bookDto = null;

        try {
            bookDto = om.readValue(resp.getBody(), BookResultDto.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        List<BookRespDto> books = bookDto.getItems();

        return new ResponseEntity<>(new ResponseDto<>(1, "도서 목록 불러오기 성공", books), HttpStatus.OK);
    }
}
