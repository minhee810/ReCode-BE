package com.abo2.recode.domain.badge;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {

    Optional<UserBadge> findByUserId(Long userId);
}
