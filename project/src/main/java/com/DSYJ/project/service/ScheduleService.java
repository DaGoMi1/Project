package com.DSYJ.project.service;

import com.DSYJ.project.controller.ScheduleDTO;
import com.DSYJ.project.domain.Schedule;
import com.DSYJ.project.repository.ScheduleRepository;
import jakarta.transaction.Transactional;

@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }


    public long scheduleSave(Schedule schedule){
        scheduleRepository.save(schedule);
        return schedule.getId();
    }
}
