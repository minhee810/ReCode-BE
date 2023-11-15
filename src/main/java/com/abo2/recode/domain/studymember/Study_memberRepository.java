package com.abo2.recode.domain.studymember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Study_memberRepository extends JpaRepository<Study_Member,Integer> {
}
