package com.recycle.recycle.service.utils;

import com.recycle.recycle.domain.Person;
import com.recycle.recycle.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonUtils {

    private PersonRepository personRepository;
    private String notFound = "Person Not Found";

    @Autowired
    public void personUtils(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getEmployees(List<String> personIds) {
        List<Person> employees = new ArrayList<>();
        if (personIds != null) {
            for (String personId : personIds) {
                Person person = personRepository.findById(personId)
                        .orElseThrow(() -> new EntityNotFoundException(notFound));
                employees.add(person);
            }
        }
        return employees;
    }
}
