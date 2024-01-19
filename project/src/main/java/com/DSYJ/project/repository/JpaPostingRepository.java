package com.DSYJ.project.repository;

import com.DSYJ.project.domain.Member;
import com.DSYJ.project.domain.Posting;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaPostingRepository implements PostingRepository{

    private final EntityManager em;

    public JpaPostingRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Posting save(Posting posting) {
        em.persist(posting);
        return posting;
    }

    @Override
    public Optional<Posting> findByTitle(String title) {
        List<Posting> result = em.createQuery("select p from Posting p where p.title=:title", Posting.class)
                .setParameter("title", title)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public Optional<Posting> findById(Long id) {
        Posting posting = em.find(Posting.class, id);
        return Optional.ofNullable(posting);
    }

    @Override
    public Optional<Posting> findByAuthor(String author) {
        List<Posting> result = em.createQuery("select p from Posting p where p.author=:author", Posting.class)
                .setParameter("author", author)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Posting> findAll() {
        return em.createQuery("select p from Posting p", Posting.class)
                .getResultList();
    }
}
