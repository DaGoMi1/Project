package com.DSYJ.project.repository;

import com.DSYJ.project.domain.Schedule;
import jakarta.persistence.EntityManager;

import java.util.List;

public class JpaScheduleRepository implements ScheduleRepository {
    private final EntityManager em;

    public JpaScheduleRepository(EntityManager em) {
        this.em = em;
    }


    @Override
    public Schedule save(Schedule schedule) {
        em.persist(schedule);
        return schedule;
    }

    @Override
    public List<Schedule> findAll() {
        return em.createQuery("select s from Schedule s", Schedule.class)
                .getResultList();
    }
}
