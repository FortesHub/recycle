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
        return getPersonIfExist(id);
    }


    public Person updatePerson(String id, PersonDTO updatedData) {
        Person person = getPersonIfExist(id);
        person.setName(updatedData.name());
        person.setTelephone(updatedData.telephone());
        person.setEmail(updatedData.email());
        person.setAddress(updatedData.address());
        return personRepository.save(person);
    }

    public void deletePerson(String id) {
        Person person = getPersonIfExist(id);
        personRepository.deleteById(id);
    }

    public void savePerson(Person person) {
        this.personRepository.save(person);
    }

    private Person getPersonIfExist(String id) {
        return personRepository.findPersonById(id)
                .orElseThrow(() -> new EntityNotFoundExceptions("Person Not Found"));
    }
}
