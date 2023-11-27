package com.abo2.recode.domain.oauth;

import com.abo2.recode.domain.user.UserEnum;
import com.nimbusds.oauth2.sdk.TokenIntrospectionSuccessResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class GoogleUser {

    @Id
    @Column(name ="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(length = 100)
    private String essay;

    @Column(nullable = false, length = 30)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserEnum role;

    private String provider;
    private String providerId;

    @Builder
    public GoogleUser(Long id, String username, String nickname, String essay, String email, UserEnum role, String provider, String providerId) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.essay = essay;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }
}
