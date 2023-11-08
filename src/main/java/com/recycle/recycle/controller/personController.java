package com.recycle.recycle.controller;

import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.PersonDTO;
import com.recycle.recycle.infra.EntityNotFoundExceptions;
import com.recycle.recycle.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.channels.ScatteringByteChannel;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/person")
public class personController {

    @Autowired
    PersonService personService;

    @PostMapping
    public ResponseEntity<Person> registerPerson(@RequestBody @Valid PersonDTO data) {
        Person newPerson = personService.registerPerson(data);
        return new ResponseEntity<>(newPerson, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> persons = this.personService.getAllPerson();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonById(@PathVariable("id") String id) {
        try {
            Person person = personService.findPersonById(id);
            return new ResponseEntity<>(person, HttpStatus.OK);
        } catch (EntityNotFoundExceptions exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<?> updatePerson(@PathVariable String id, @RequestBody PersonDTO updatedData) {
//        try {
//            Optional<Person> person = personService.updatePerson(id, updatedData);
//            if (person.isPresent()) {
//                return new ResponseEntity<>(person.get(), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(exception., HttpStatus.NOT_FOUND);
//            }
//        }
//
//        @DeleteMapping("/{id}")
//        public ResponseEntity<?> deletePerson (@PathVariable String id){
//            Optional<Person> person = personService.deletePerson(id);
//            if (person.isPresent()) {
//                return new ResponseEntity<>("Person deleted successfully", HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("Person Not Found", HttpStatus.NOT_FOUND);
//            }
//        }
    }
