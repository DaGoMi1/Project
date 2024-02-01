package com.DSYJ.project.repository;

import com.DSYJ.project.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJpaCommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPostingId(Long postingId);
}
