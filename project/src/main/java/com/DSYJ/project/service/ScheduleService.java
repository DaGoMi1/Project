package com.DSYJ.project.service;

import com.DSYJ.project.domain.Schedule;
import com.DSYJ.project.repository.SpringDataJpaScheduleRepository;
import jakarta.transaction.Transactional;

@Transactional
public class ScheduleService {

    private final SpringDataJpaScheduleRepository scheduleRepository;

    public ScheduleService(SpringDataJpaScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }


    public void scheduleSave(Schedule schedule){
        scheduleRepository.save(schedule);
    }
}
