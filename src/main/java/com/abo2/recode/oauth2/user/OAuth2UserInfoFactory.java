package com.abo2.recode.oauth2.user;

import com.abo2.recode.handler.ex.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId,
                                                   String accessToken,
                                                   Map<String, Object> attributes) throws OAuth2AuthenticationProcessingException {
        if (OAuth2Provider.GOOGLE.getRegistrationId().equals(registrationId)) {
            return new GoogleOAuth2UserInfo(accessToken, attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Login with " + registrationId + " is not supported");
        }
    }
}
