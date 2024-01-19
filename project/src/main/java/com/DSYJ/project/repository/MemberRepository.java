package com.DSYJ.project.repository;

import com.DSYJ.project.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findByUserIdAndPassword(String userId, String password);
    Optional<Member> findById(Long Id);
    Optional<Member> findByUserId(String userId);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
