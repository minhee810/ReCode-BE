package com.abo2.recode.domain.studymember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Study_memberRepository extends JpaRepository<Study_Member,Integer> {

    @Query("SELECT sm FROM Study_Member sm WHERE sm.user.id = :userId AND (sm.status = 0 OR sm.status = 1)")
    List<Study_Member> findStudyMembersByUserId(Long userId);

}
