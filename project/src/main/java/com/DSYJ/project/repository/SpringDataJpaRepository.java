package com.DSYJ.project.repository;

import com.DSYJ.project.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpringDataJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);

    default void updateMemberName(Long memberId, String newName) {
        findById(memberId).ifPresent(member -> {
            member.setName(newName);
            save(member);
        });
    }

}
