package com.bezkoder.spring.data.jpa.pagingsorting.controller;

import com.bezkoder.spring.data.jpa.pagingsorting.model.ResponseModel;
import com.bezkoder.spring.data.jpa.pagingsorting.model.Tutorial;
import com.bezkoder.spring.data.jpa.pagingsorting.repository.TutorialRepository;
import com.bezkoder.spring.data.jpa.pagingsorting.service.TutorialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TutorialController {

  @Autowired
  private TutorialRepository tutorialRepository;
  @Autowired
  private TutorialService tutorialService;

  private Sort.Direction getSortDirection(String direction) {
    if (direction.equals("asc")) {
      return Sort.Direction.ASC;
    } else if (direction.equals("desc")) {
      return Sort.Direction.DESC;
    }
    return Sort.Direction.ASC;
  }

  @Cacheable(value = "tutorials-simple")
  @GetMapping("/tutorials/simple")
  public List<Tutorial> getAllTutorials(){
    return tutorialRepository.findAll();
  }

  //@Cacheable(value = "tutorials-with-pagination") not working due string array
  @GetMapping("/tutorials")
  public ResponseModel getAllTutorialsPage(
      @RequestParam(required = false) String title,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "3") int size,
      @RequestParam(defaultValue = "id,desc") String[] sort) {
    try {
      List<Order> orders = new ArrayList<>();
      for (String sortOrder : sort) {
        if (sort[0].contains(",")) {
          String[] sort_components = sortOrder.split(",");
          orders.add(new Order(getSortDirection(sort_components[1]), sort_components[0]));
        }
      }
      Page<Tutorial> tutorials = tutorialService.getTutorials(title,  PageRequest.of(page, size, Sort.by(orders)));
      ResponseModel<Tutorial> responseModel = new ResponseModel<>();
      responseModel.setElements(tutorials.getContent());
      responseModel.setPageNumber(tutorials.getNumber());
      responseModel.setTotalPages(tutorials.getTotalPages());
      responseModel.setPageSize(tutorials.getSize());
      responseModel.setTotalElements(tutorials.getTotalElements());
      return responseModel;
    } catch (Exception e) {
      log.error("Error",e);
      return null;
    }
  }

  @GetMapping("/tutorials/{id}")
  public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
    Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
    if (tutorialData.isPresent()) {
      return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/tutorials")
  public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
    try {
      Tutorial _tutorial = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
      return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/tutorials/{id}")
  public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
    Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

    if (tutorialData.isPresent()) {
      Tutorial _tutorial = tutorialData.get();
      _tutorial.setTitle(tutorial.getTitle());
      _tutorial.setDescription(tutorial.getDescription());
      _tutorial.setPublished(tutorial.isPublished());
      return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/tutorials/{id}")
  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
    try {
      tutorialRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/tutorials")
  public ResponseEntity<HttpStatus> deleteAllTutorials() {
    try {
      tutorialRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
