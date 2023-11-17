package com.abo2.recode.domain.studymember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Study_memberRepository extends JpaRepository<Study_Member,Long> {

    List<Study_Member> findByUserId(Long userId);

    void deleteById(Long userId);

}
