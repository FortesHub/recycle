package com.recycle.recycle.mapper;

import com.recycle.recycle.domain.AddressKey;
import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.dto.AddressKeyDTO;
import com.recycle.recycle.dto.PersonDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

class PersonMapperTest {
    private PersonMapper personMapper = Mappers.getMapper(PersonMapper.class);
    @Test
    void convertToDTO() {
        Person person = new Person();
        person.setName("IGA");
        person.setEmail("iga@iga");
        PersonDTO personDTO = personMapper.convertToDTO(person);
        assertEquals(person.getName(), personDTO.name());
    }

    @Test
    void convertToPerson() {
        AddressKeyDTO addressKey = new AddressKeyDTO("Rue Saint Jean", "2", "J2w2T5");
        PersonDTO personDTO = new PersonDTO("iga", "438-111-1111", "iga@iga.com", new AddressDTO(addressKey, "Saint Jean", "Canada"));
        Person person = personMapper.convertToPerson(personDTO);
        assertEquals(personDTO.name(), person.getName());
    }
}