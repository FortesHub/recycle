package com.recycle.recycle.service;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.domain.Company;
import com.recycle.recycle.domain.Establishment;
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
    private PersonRepository personRepository;
    private EntityService entityService;
    private PersonMapper personMapper;

    @Autowired
    public void personService(PersonRepository personRepository,
                              PersonMapper personMapper,
                              EntityService entityService) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.entityService = entityService;
    }

    public PersonDTO registerPerson(PersonDTO personDTO) {
        List<Company> companies = entityService.getCompanies(personDTO.companyIds());
        List<Establishment> establishments = entityService.getEstablishments(personDTO.establishmentIds());
        Person newPerson = personMapper.convertToPerson(personDTO);
        Address address = entityService.getAddress(newPerson.getAddress());
        newPerson.setAddress(address);
        newPerson.setCompanies(companies);
        newPerson.setEstablishments(establishments);

        Person savedPerson = personRepository.save(newPerson);
        return personMapper.convertToDTO(savedPerson);
    }

    public List<Person> getAllPerson() {
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(String personId) {
        return personRepository.findById(personId);
    }

    public PersonDTO updatePerson(String personId, PersonDTO personDTO) {
        List<Company> companies = entityService.getCompanies(personDTO.companyIds());
        List<Establishment> establishments = entityService.getEstablishments(personDTO.establishmentIds());
        Person personToUpdate = personMapper.convertToPerson(personDTO);
        Address address = entityService.getAddress(personToUpdate.getAddress());
        personToUpdate.setAddress(address);
        personToUpdate.setCompanies(companies);
        personToUpdate.setEstablishments(establishments);
        personToUpdate.setPersonId(personId);
        Person updatedPerson = personRepository.save(personToUpdate);
        return personMapper.convertToDTO(updatedPerson);
    }

    public boolean deletePerson(String personId) {
        Optional<Person> personToDelete = getPersonById(personId);
        if (personToDelete.isPresent()) {
            personRepository.delete(personToDelete.get());
            return true;
        }
        return false;
    }

}
