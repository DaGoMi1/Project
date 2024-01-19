package com.DSYJ.project.repository;

import com.DSYJ.project.domain.Member;
import com.DSYJ.project.domain.Posting;

import java.util.List;
import java.util.Optional;

public interface PostingRepository {
    Posting save(Posting posting);
    Optional<Posting> findByTitle(String title);
    Optional<Posting> findById(Long id);
    Optional<Posting> findByAuthor(String author);
    List<Posting> findAll();
}
