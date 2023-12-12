package com.abo2.recode.domain.notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface NotificationRepository extends JpaRepository<Notifications, Long> {


    // 사용자의 알림 조회
    List<Notifications> findByUserId(Long userId);

}
