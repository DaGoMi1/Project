package com.DSYJ.project.config;

import com.DSYJ.project.repository.*;
import com.DSYJ.project.service.MemberService;
import com.DSYJ.project.service.PostingService;
import com.DSYJ.project.service.ScheduleService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private final EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public PostingService postingService() {
        return new PostingService(postingRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new JpaMemberRepository(em);
    }

    @Bean
    public PostingRepository postingRepository() {
        return new JpaPostingRepository(em);
    }

    @Bean
    public ScheduleService scheduleService() {
        return new ScheduleService(scheduleRepository());
    }

    @Bean
    public ScheduleRepository scheduleRepository() {
        return new JpaScheduleRepository(em);
    }
}
