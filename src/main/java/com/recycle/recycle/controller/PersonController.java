package com.recycle.recycle.controller;

import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.PersonDTO;
import com.recycle.recycle.service.PersonService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController()
@RequestMapping("/person")
public class PersonController {
    private PersonService personService;
    @Autowired
    public void personController(PersonService personService){
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<PersonDTO> registerPerson(@Valid @RequestBody PersonDTO personDTO) {
        PersonDTO newPerson = personService.registerPerson(personDTO);
        return new ResponseEntity<>(newPerson, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> persons = this.personService.getAllPerson();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonById(@PathVariable("id") String id) {
        Optional<Person> person = personService.getPersonById(id);
        if(person.isEmpty()){
            throw new EntityNotFoundException("Person Not Found");
        }
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable String id, @RequestBody @Valid PersonDTO
            personDTO) {
        PersonDTO person = personService.updatePerson(id, personDTO);
        if(person == null){
            throw new EntityNotFoundException("Person Not Found");
        }
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable String id) {
        Boolean wasDeleted = personService.deletePerson(id);
        if (!wasDeleted) {
            throw new EntityNotFoundException("Person Not Found");
        }
        return new ResponseEntity<>("Person deleted successfully!", HttpStatus.OK);
    }
}
