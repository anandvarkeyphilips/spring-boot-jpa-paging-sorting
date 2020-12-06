package com.bezkoder.spring.data.jpa.pagingsorting.service;

import com.bezkoder.spring.data.jpa.pagingsorting.model.Tutorial;
import com.bezkoder.spring.data.jpa.pagingsorting.repository.TutorialRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TutorialService {

    @Autowired
    TutorialRepository tutorialRepository;

    public Page<Tutorial> getTutorials(String title, Pageable pageable, Predicate predicate){
        Page<Tutorial> tutorialPage;
        if (title == null) {
            tutorialPage = tutorialRepository.findAll(predicate, pageable);
        }else {
            tutorialPage = tutorialRepository.findByTitleContaining(title, pageable);
        }
        return tutorialPage;
    }
}
