package com.abo2.recode.service;

import com.abo2.recode.domain.oauth.GoogleUser;
import com.abo2.recode.domain.oauth.GoogleUserRepository;
import com.abo2.recode.domain.user.UserEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import com.abo2.recode.handler.ex.OAuth2AuthenticationProcessingException;
import com.abo2.recode.oauth2.user.OAuth2UserInfo;
import com.abo2.recode.oauth2.user.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);
    private final GoogleUserRepository googleUserRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        logger.info("getAttributes ; {}", oAuth2User.getAttributes());

        String provider = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider + "_" + providerId;

        Optional<GoogleUser> optionalUser = googleUserRepository.findByUsername(username);
        GoogleUser googleUser;

        if(optionalUser.isEmpty()) {
            String email = oAuth2User.getAttribute("email");
            googleUser = GoogleUser.builder()
                    .username(username)
                    .nickname(oAuth2User.getAttribute("name"))
                    .email(email)
                    .provider(provider)
                    .providerId(providerId)
                    .role(UserEnum.CUSTOMER)
                    .build();
            googleUserRepository.save(googleUser);
        } else {
            googleUser = optionalUser.get();
        }

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            try {
                throw ex;
            } catch (OAuth2AuthenticationProcessingException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) throws OAuth2AuthenticationProcessingException {

        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId();

        String accessToken = userRequest.getAccessToken().getTokenValue();

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId,
                accessToken,
                oAuth2User.getAttributes());

        if (!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        return new OAuth2UserPrincipal(oAuth2UserInfo);
    }
}
