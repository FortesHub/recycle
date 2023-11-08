package com.recycle.recycle.service;

import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.PersonDTO;
import com.recycle.recycle.infra.EntityNotFoundExceptions;
import com.recycle.recycle.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;

    public Person registerPerson(PersonDTO data) {
        Person newPerson = new Person(data);
        this.savePerson(newPerson);
        return newPerson;
    }

    public List<Person> getAllPerson() {
        return this.personRepository.findAll();
    }

    public Person findPersonById(String id) {
      return  personRepository.findPersonById(id)
              .orElseThrow(() -> new EntityNotFoundExceptions("Person id Not Found"));
        }


    public Optional<Person> updatePerson(String id, PersonDTO updatedData) {
        Optional<Person> person = personRepository.findPersonById(id);
        if (person.isPresent()) {
            Person updatePerson = person.get();
            updatePerson.setName(updatedData.name());
            updatePerson.setTelephone(updatedData.telephone());
            updatePerson.setEmail(updatedData.email());
            updatePerson.setAddress(updatedData.address());
            personRepository.save(updatePerson);
            return Optional.of(updatePerson);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Person> deletePerson(String id) {
        Optional<Person> person = personRepository.findPersonById(id);
        if (person.isPresent()) {
            personRepository.deleteById(id);
        } else {
            Optional.empty();
        }
        return person;
    }

    public void savePerson(Person person) {
        this.personRepository.save(person);
    }
}
