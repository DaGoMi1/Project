package com.DSYJ.project.service;

import com.DSYJ.project.domain.Comment;
import com.DSYJ.project.domain.Posting;
import com.DSYJ.project.repository.SpringDataJpaCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CommentService {
    private final SpringDataJpaCommentRepository commentRepository;

    @Autowired
    public CommentService(SpringDataJpaCommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void save(Comment comment){
        commentRepository.save(comment);
    }
    public List<Comment> findByPostingId(Long id) {
        return commentRepository.findByPostingId(id);
    }
}
