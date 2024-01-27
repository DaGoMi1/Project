package com.DSYJ.project.repository;

import com.DSYJ.project.domain.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaPostingRepository extends JpaRepository<Posting, Long>{
}
