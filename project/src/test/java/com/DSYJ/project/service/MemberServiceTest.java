package com.DSYJ.project.service;

import com.DSYJ.project.domain.Member;
import com.DSYJ.project.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    public void 중복_회원_검증() {
        //given
        Member member1 = new Member();
        member1.setUserId("yylo220");

        Member member2 = new Member();
        member2.setUserId("yylo22");

        //when
        memberService.join(member1);
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }

    @Test
    void findMember() {
    }

    @Test
    void findOne() {
    }
}