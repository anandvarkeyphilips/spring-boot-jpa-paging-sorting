package com.bezkoder.spring.data.jpa.pagingsorting.repository;

import com.bezkoder.spring.data.jpa.pagingsorting.model.Tutorial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Long>, QuerydslPredicateExecutor<Tutorial> {

    Page<Tutorial> findByTitleContaining(String title, Pageable pageable);
}
