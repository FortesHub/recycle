package com.recycle.recycle.service;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.domain.Company;
import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.PersonDTO;
import com.recycle.recycle.mapper.PersonMapper;
import com.recycle.recycle.repository.AddressRepository;
import com.recycle.recycle.repository.CompanyRepository;
import com.recycle.recycle.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private PersonRepository personRepository;
    private AddressRepository addressRepository;
    private CompanyRepository companyRepository;
    private PersonMapper personMapper;

    @Autowired
    public void personService(PersonRepository personRepository,
                              PersonMapper personMapper,
                              AddressRepository addressRepository,
                              CompanyRepository companyRepository) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.addressRepository = addressRepository;
        this.companyRepository = companyRepository;
    }

    public PersonDTO registerPerson(PersonDTO personDTO) {
        List<Company> companies = getCompanies(personDTO);
        Person newPerson = personMapper.convertToPerson(personDTO);
        newPerson.setCompanies(companies);
        getAddress(newPerson);
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
        List<Company> companies = getCompanies(personDTO);
        Person personToUpdate = personMapper.convertToPerson(personDTO);
        personToUpdate.setCompanies(companies);
        getAddress(personToUpdate);
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

    public Person getAddress(Person person) {
        Address existingAddress = addressRepository.findByAddressComposite(person.getAddress().getAddressComposite());
        if (existingAddress != null) {
            person.setAddress(existingAddress);
        } else {
            Address address = addressRepository.save(person.getAddress());
            person.setAddress(address);
        }
        return person;
    }

    public List<Company> getCompanies(PersonDTO personDTO) {
        List<Company> companies = new ArrayList<>();
        if (personDTO.companyIds() != null) {
            for (String companyId : personDTO.companyIds()) {
                Company company = companyRepository.findById(companyId)
                        .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + companyId));
                companies.add(company);
            }
        }
        return companies;
    }
}
