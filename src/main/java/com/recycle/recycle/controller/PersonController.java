package com.recycle.recycle.controller;

import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.ExceptionDTO;
import com.recycle.recycle.dto.PersonDTO;
import com.recycle.recycle.service.CompanyService;
import com.recycle.recycle.service.PersonService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {
    private PersonService personService;
    private String notFound = "Person Not Found!";
    private String alreadyExist = "Person Already Exist";
    private String deleted = "Person deleted successfully!";
    @Autowired
    public void personController(PersonService personService){
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<?> registerPerson(@Valid @RequestBody PersonDTO personDTO) {
        try {
            PersonDTO newPerson = personService.registerPerson(personDTO);
            return new ResponseEntity<>(newPerson, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            ExceptionDTO exception = new ExceptionDTO(alreadyExist, HttpStatus.CONFLICT.value());
            return ResponseEntity.status(exception.statusCode()).body(exception);
        }
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> persons = personService.getAllPerson();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/{personId}")
    public ResponseEntity<?> getPersonById(@PathVariable("personId") String personId) {
        Optional<Person> person = personService.getPersonById(personId);
        if (person.isPresent()) {
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
        throw new EntityNotFoundException(notFound);
    }

    @PutMapping("/{personId}")
    public ResponseEntity<?> updatePerson(@PathVariable("personId") String personId, @RequestBody @Valid PersonDTO
            personDTO) {
        try {
            PersonDTO updatedPerson = personService.updatePerson(personId, personDTO);
            return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            ExceptionDTO exception = new ExceptionDTO(notFound, HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(exception.statusCode()).body(exception);
        }
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<?> deletePerson(@PathVariable String personId) {
        Boolean wasDeleted = personService.deletePerson(personId);
        if (wasDeleted) {
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        }
        throw new EntityNotFoundException(notFound);
    }
}
