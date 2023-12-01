package com.recycle.recycle.service;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.dto.PersonDTO;
import com.recycle.recycle.mapper.PersonMapper;
import com.recycle.recycle.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;

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
    private List<Address> addressList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        personDTO = new PersonDTO(
                "John",
                "438-111-1111",
                "john@john.ca",
                new AddressDTO("rue des johns",
                        "3",
                        "j4k4j4",
                        "Saint Jean Sur Richelieu",
                        "Canada")
        );

        addressList = Arrays.asList(
                new Address(321L, "rue des johns", "3", "j4k4j4", "Saint Jean Sur Richelieu", "Canada"),
                new Address(789L, "rue des Brin", "4", "j4k4j4", "Saint Jean Sur Richelieu", "Canada")
        );

        personList = Arrays.asList(
                new Person("321", "John", "438-111-1111", "john@john.ca", addressList.get(0)),
                new Person("789", "bron", "438-111-1111", "bron@bron.ca", addressList.get(1))
        );
    }

    @DisplayName("Test for register person and return Person")
    @Test
    void givenPersonObject_whenRegisterPerson() {
        when(personRepository.save(any(Person.class))).thenReturn(personList.get(0));
        PersonDTO resultDTO = personService.registerPerson(personDTO);

        verify(personRepository, times(1)).save(any(Person.class));
        verify(personMapper, times(1)).convertToPerson(personDTO);
        verify(personMapper, times(1)).convertToDTO(personList.get(0));
        assertEquals(personDTO, resultDTO);
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
        PersonDTO updatedPersonDTO = new PersonDTO("updatedJohn", "438-111-1111", "updatedjohn@gmail.com", new AddressDTO("rue des johns",
                "3", "j4k4j4", "Saint Jean Sur Richelieu", "Canada"));

        when(personRepository.findById(id)).thenReturn(Optional.of(personList.get(0)));
        when(personRepository.save(any())).thenReturn(personList.get(0));
        PersonDTO resultDTO = personService.updatePerson(id, updatedPersonDTO);

        verify(personRepository, times(1)).findById(id);
        verify(personRepository, times(1)).save(any(Person.class));
        verify(personMapper, times(1)).updateDTOToPerson(updatedPersonDTO, personList.get(0));
        verify(personMapper, times(1)).convertToDTO(personList.get(0));

        assertEquals("updatedJohn", resultDTO.name());
        assertEquals("438-111-1111", resultDTO.telephone());
        assertEquals("updatedjohn@gmail.com", resultDTO.email());
        assertEquals("rue des johns", resultDTO.address().street());
        assertEquals("3", resultDTO.address().complement());
        assertEquals("j4k4j4", resultDTO.address().postalCode());
        assertEquals("Saint Jean Sur Richelieu", resultDTO.address().city());
        assertEquals("Canada", resultDTO.address().pays());
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