package com.abo2.recode.config.oauth2.client;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;

public enum CommonOAuth2Provider {

    DEFAULT_REDIRECT_URL, GOOGLE {
        public ClientRegistration.Builder getBuilder(String registrationId) {
            ClientRegistration.Builder builder = getBuilder(registrationId,
                    ClientAuthenticationMethod.CLIENT_SECRET_BASIC, DEFAULT_REDIRECT_URL);
            builder.scope("openId", "profile", "email");
            builder.authorizationUri("http://accounts.google.com/o/oauth2/v2/outh");
            builder.tokenUri("http://www.googleapis.com/oauth/v4/token");
            builder.jwkSetUri("http://www.googleapis.com/oauth2/v3/certs");
            builder.issuerUri("http://accounts.google.com");
            builder.userInfoUri("http://www.googleapis.com/oauth2/v3/userInfo");
            builder.userNameAttributeName(IdTokenClaimNames.SUB);
            builder.clientName("Google");
            return builder;
        }

        private ClientRegistration.Builder getBuilder(String registrationId, ClientAuthenticationMethod clientSecretBasic, CommonOAuth2Provider commonOAuth2Provider) {
            return null;
        }
    }
}
