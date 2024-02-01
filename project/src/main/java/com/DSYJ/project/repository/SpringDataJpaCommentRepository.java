package com.DSYJ.project.repository;

import com.DSYJ.project.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaCommentRepository extends JpaRepository<Comment,Long> {
}
