package com.abo2.recode.domain.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface NotificationRepository extends JpaRepository<Notifications, Long> {

    @Query(value = "DELETE FROM Notifications WHERE study_member_id=:memberId",nativeQuery = true)
    void deleteByMemberId(@Param(value = "memberId") Long memberId);
}
