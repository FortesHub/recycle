package com.recycle.recycle.mapper;

import com.recycle.recycle.domain.AddressComposite;
import com.recycle.recycle.domain.Company;
import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.AddressCompositeDTO;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.dto.PersonDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonMapperTest {
    private PersonMapper personMapper = Mappers.getMapper(PersonMapper.class);

    @Test
    void convertToDTO() {
        Person person = new Person();
        person.setName("IGA");
        person.setEmail("iga@iga");

        List<Company> companies = new ArrayList<>();
        Company company1 = new Company();
        company1.setName("company1");

        person.setCompanies(companies);

        PersonDTO personDTO = personMapper.convertToDTO(person);
        assertEquals(person.getName(), personDTO.name());
    }

    @Test
    void convertToPerson() {
        AddressCompositeDTO addressKey = new AddressCompositeDTO("Rue Saint Jean", "2", "J2w2T5");
        AddressDTO addressDTO = new AddressDTO(addressKey, "Saint Jean", "Canada");
        List<String> companyIds = Arrays.asList("123");

        PersonDTO personDTO = new PersonDTO("iga", "438-111-1111", "iga@iga.com", addressDTO, companyIds);
        Person person = personMapper.convertToPerson(personDTO);
        assertEquals(personDTO.name(), person.getName());
    }
}