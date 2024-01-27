package com.DSYJ.project.repository;

import com.DSYJ.project.domain.Posting;
import com.DSYJ.project.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaScheduleRepository extends JpaRepository<Schedule, Long> {
}
