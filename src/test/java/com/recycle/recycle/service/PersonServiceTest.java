package com.recycle.recycle.service;

import com.recycle.recycle.domain.*;
import com.recycle.recycle.dto.AddressCompositeDTO;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.dto.PersonDTO;
//import com.recycle.recycle.mapper.PersonMapper;
import com.recycle.recycle.mapper.PersonMapper;
import com.recycle.recycle.repository.AddressRepository;
import com.recycle.recycle.repository.CompanyRepository;
import com.recycle.recycle.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @InjectMocks
    private PersonService personService;
    @Mock
    private PersonRepository personRepository;
    @Spy
    private PersonMapper personMapper = Mappers.getMapper(PersonMapper.class);
    private PersonDTO personDTO;
    private List<Person> personList;

    @BeforeEach
    public void setUp() {
        personDTO = new PersonDTO("John Doe", "123456789", "john.doe@example.com");
        personList = Arrays.asList(
                new Person("personId123", "John Doe", "123456789",
                        "john.doe@example.com"));

   }

    @DisplayName("Test for register person and return Person")
    @Test
    void givenPersonObject_whenRegisterPerson() {
        when(personRepository.save(any())).thenReturn(personList.get(0));
        PersonDTO result = personService.registerPerson(personDTO);
        verify(personRepository, times(1)).save(any());
        assertNotNull(result);
        assertEquals(personDTO.name(), result.name());
    }


    @DisplayName("Test for get all person and return list of person")
    @Test
    void givenPersonList_whenGetAllPerson() {
        when(personRepository.findAll()).thenReturn(personList);
        List<Person> resultList = personService.getAllPerson();
        assertEquals(personList.size(), resultList.size());
        verify(personRepository, times(1)).findAll();
    }

    @DisplayName("Test for find person By Id and return Person")
    @Test
    void givenOptionalPerson_whenGetPersonById() {
        String personId = "321";
        when(personRepository.findById(personId)).thenReturn(Optional.of(personList.get(0)));
        Optional<Person> resultPerson = personService.getPersonById(personId);
        verify(personRepository, times(1)).findById(personId);
        assertEquals(Optional.of(personList.get(0)), resultPerson);
    }

    @DisplayName("Test for update Person with existing Id")
    @Test
    void givenIdPersonAndUpdatePerson() {
        String id = "321";
        personDTO = new PersonDTO("John", "123456789", "john.doe@example.com");
        Person personDTOtoPerson =  new Person("321", "updatedJohn", "438-111-1111", "updatedjohn@gmail.com");

        when(personRepository.save(any(Person.class))).thenReturn(personDTOtoPerson);
        PersonDTO resultDTO = personService.updatePerson(personDTOtoPerson.getPersonId(), personDTO);
        verify(personRepository, times(1)).save(any(Person.class));
        assertEquals("updatedJohn", resultDTO.name());
        assertEquals("438-111-1111", resultDTO.telephone());
        assertEquals("updatedjohn@gmail.com", resultDTO.email());
    }

    @DisplayName("Test for find person By Id and delete Person")
    @Test
    void givenIdPersonAndDeletePerson() {
        String personId = "321";
        when(personRepository.findById(personId)).thenReturn(Optional.of(personList.get(0)));
        boolean resultPerson = personService.deletePerson(personId);
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(1)).delete(personList.get(0));
        assertTrue(resultPerson);
    }
}