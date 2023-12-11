package com.abo2.recode.domain.badge;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {

    Badge findByLevel(String level);
}
