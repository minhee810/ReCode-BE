package com.abo2.recode.domain.notification;

import com.abo2.recode.dto.Notification.NotificationRespDto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotificationRepository extends JpaRepository<Notifications, Long> {

}
