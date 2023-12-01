package com.recycle.recycle.service;

import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.PersonDTO;
import com.recycle.recycle.mapper.PersonMapper;
import com.recycle.recycle.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonMapper personMapper;

    public PersonDTO registerPerson(PersonDTO personDTO) {
        Person newPerson = personMapper.convertToPerson(personDTO);
        Person savedPerson = this.personRepository.save(newPerson);
        return personMapper.convertToDTO(savedPerson);
    }

    public List<Person> getAllPerson() {
        return this.personRepository.findAll();
    }

    public Optional<Person> getPersonById(String id) {
        return getPerson(id);
    }

    public PersonDTO updatePerson(String id, PersonDTO personDTO) {
        Optional<Person> existingPerson = getPerson(id);
        if (existingPerson.isPresent()) {
            Person personToUpdate = existingPerson.get();
            personMapper.updateDTOToPerson(personDTO, personToUpdate);
            Person updatedPerson = this.personRepository.save(personToUpdate);
            return personMapper.convertToDTO(updatedPerson);
        }
        return null;
    }

    public boolean deletePerson(String id) {
        Optional<Person> personToDelete = getPerson(id);
        if (personToDelete.isPresent()) {
            personRepository.delete(personToDelete.get());
            return true;
        }
        return false;
    }

    private Optional<Person> getPerson(String id) {
        return personRepository.findById(id);
    }
}


