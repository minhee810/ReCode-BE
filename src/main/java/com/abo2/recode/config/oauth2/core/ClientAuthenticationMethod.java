package com.abo2.recode.config.oauth2.core;

import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import java.io.Serializable;

/*
    client-authentication-method 값이 client_secret_post 이면 파라미터에 clientId, clientSecret 을 담는 것을 볼 수 있다.
    네이버, 카카오는 인가 코드로 토큰 요청시 clientId, clientSecret 값을 담아서 보내기 때문에 client_secret_post 을 사용
*/
public final class ClientAuthenticationMethod implements Serializable {

    private static final long serialVerSionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    public static final ClientAuthenticationMethod CLIENT_SECRET_BASIC = new ClientAuthenticationMethod(
            "client_secret_basic");

    public static final ClientAuthenticationMethod CLIENT_SECRET_POST = new ClientAuthenticationMethod(
            "client_secret_post");

    public static final ClientAuthenticationMethod CLIENT_SECRET_JWT = new ClientAuthenticationMethod(
            "client_secret_jwt");

    public static final ClientAuthenticationMethod PRIVATE_KEY_JWT = new ClientAuthenticationMethod(
            "private_key_jwt");

    public static final ClientAuthenticationMethod NONE = new ClientAuthenticationMethod("none");

    private final String value;

    public ClientAuthenticationMethod(String value) {
        Assert.hasText(value, "value cannot be empty");
        this.value = value;
    }
}
