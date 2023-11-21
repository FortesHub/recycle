package com.recycle.recycle.service;

import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.dto.RegisterPersonDTO;
import com.recycle.recycle.mapper.PersonMapper;
import com.recycle.recycle.repository.PersonRepository;

import io.micrometer.common.util.StringUtils;
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
        Person newPerson = personMapper.personDTOToPerson(data);
        this.savePerson(newPerson);
        return personMapper.personToPersonDTO(newPerson);
    }

    public List<Person> getAllPerson() {
        return this.personRepository.findAll();
    }

    public Person findPersonById(String id) {
        return getPersonIfExist(id);
    }

    public RegisterPersonDTO updatePerson(String id, RegisterPersonDTO updatedData) {
        Person person = getPersonIfExist(id);
        personMapper.updatePersonFromDTO(updatedData, person);
        personRepository.save(person);
        return personMapper.personToPersonDTO(person);
    }

    public void deletePerson(String id) {
        getPersonIfExist(id);
        personRepository.deleteById(id);
    }

    public Person savePerson(Person person) {
        this.personRepository.save(person);
        return person;
    }

    private Person getPersonIfExist(String id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person Not Found"));
    }

}


