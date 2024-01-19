package com.DSYJ.project.repository;

import com.DSYJ.project.domain.Schedule;

import java.util.List;

public interface ScheduleRepository {
    Schedule save(Schedule schedule);
    List<Schedule> findAll();
}
