package com.abo2.recode.domain.oauth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoogleUserRepository extends JpaRepository<GoogleUser, Long> {

    Optional<GoogleUser> findByUsername(String username);
}
