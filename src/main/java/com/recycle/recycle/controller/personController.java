package com.recycle.recycle.controller;

import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.RegisterPersonDTO;
import com.recycle.recycle.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController()
@RequestMapping("/person")
public class personController {

    @Autowired
    PersonService personService;

    @PostMapping
    public ResponseEntity<RegisterPersonDTO> registerPerson(@Valid @RequestBody RegisterPersonDTO data) {
        RegisterPersonDTO newPerson = personService.registerPerson(data);
        return new ResponseEntity<>(newPerson, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> persons = this.personService.getAllPerson();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonById(@PathVariable("id") String id) {
        Person person = personService.findPersonById(id);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegisterPersonDTO> updatePerson(@PathVariable String id, @RequestBody @Valid RegisterPersonDTO updatedData) {
        RegisterPersonDTO updatedperson = personService.updatePerson(id, updatedData);
        return new ResponseEntity<>(updatedperson, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable String id) {
        personService.deletePerson(id);
        return new ResponseEntity<>("Person deleted successfully!", HttpStatus.OK);
    }
}
