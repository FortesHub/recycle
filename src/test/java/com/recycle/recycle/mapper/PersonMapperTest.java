package com.recycle.recycle.mapper;

import com.recycle.recycle.domain.AddressComposite;
import com.recycle.recycle.domain.Company;
import com.recycle.recycle.domain.Establishment;
import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.AddressCompositeDTO;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.dto.PersonDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonMapperTest {
    private PersonMapper personMapper = Mappers.getMapper(PersonMapper.class);
    private Person person;
    private PersonDTO personDTO;

    @BeforeEach
    public void setUp() {
        person = new Person("personId123", "John Doe", "123456789",
                "john.doe@example.com");
        personDTO = new PersonDTO("John Doe", "123456789",
                "john.doe@example.com");
    }
    @Test
    void convertToDTO() {
        PersonDTO personDTO = personMapper.convertToDTO(person);
        assertEquals(person.getName(), personDTO.name());
    }

    @Test
    void convertToPerson() {
        Person person = personMapper.convertToPerson(personDTO);
        assertEquals(personDTO.name(), person.getName());
    }
}