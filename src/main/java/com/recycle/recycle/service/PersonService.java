package com.recycle.recycle.service;

import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.RegisterPersonDTO;
import com.recycle.recycle.mapper.PersonMapper;
import com.recycle.recycle.repository.PersonRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonMapper personMapper;

    public RegisterPersonDTO registerPerson(RegisterPersonDTO data) {
        Person newPerson = personMapper.convertToPerson(data);
        Person savedPerson = this.personRepository.save(newPerson);
        return personMapper.convertToDTO(savedPerson);
    }

    public List<Person> getAllPerson() {
        return this.personRepository.findAll();
    }

    public Person findPersonById(String id) {
        return getPersonIfExist(id);
    }

    public RegisterPersonDTO updatePerson(String id, RegisterPersonDTO data) {
        Person existingPerson = getPersonIfExist(id);
        existingPerson = personMapper.updateDTOToPerson(data, existingPerson);
        Person updatedPerson = this.personRepository.save(existingPerson);
        RegisterPersonDTO resultPerson = personMapper.convertToDTO(updatedPerson);
        return resultPerson;
    }

    public void deletePerson(String id) {
        getPersonIfExist(id);
        personRepository.deleteById(id);
    }

    private Person getPersonIfExist(String id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person Not Found"));
    }
}


