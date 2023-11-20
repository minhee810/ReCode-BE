package com.abo2.recode.handler.ex;

import lombok.Getter;

import java.util.Map;

@Getter
public class CustomValidationException extends RuntimeException {

    private Map<String, String> errorMap;

    public CustomValidationException(String message, Map<String, String> errorMap) {
        super(message);
        this.errorMap = errorMap;
    }

    // 아이디 중복 예외를 생성하는 정적 팩토리 메서드

}
