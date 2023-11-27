package com.recycle.recycle.service;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.dto.RegisterPersonDTO;
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
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    private RegisterPersonDTO mockedDTO;
    private List<Person> mockedPersonList;
    private List<Address> mockedAddressList;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockedDTO = new RegisterPersonDTO(
                "John",
                "438-111-1111",
                "john@john.ca",
                new AddressDTO("rue des johns",
                        "3",
                        "j4k4j4",
                        "Saint Jean Sur Richelieu",
                        "Canada")
        );

        mockedAddressList = Arrays.asList(
                new Address(321L, "rue des johns", "3", "j4k4j4", "Saint Jean Sur Richelieu", "Canada"),
                new Address(789L, "rue des Brin", "4", "j4k4j4", "Saint Jean Sur Richelieu", "Canada")
        );
        mockedPersonList = Arrays.asList(
                new Person("321", "John", "438-111-1111", "john@john.ca", mockedAddressList.get(0)),
                new Person("789", "bron", "438-111-1111", "bron@bron.ca", mockedAddressList.get(1))
        );
    }

    @DisplayName("Test for registerPerson and return personObject")
    @Test
    void givenPersonObject_whenRegister_returnPersonObject() {
        when(personMapper.convertToPerson(mockedDTO)).thenReturn(mockedPersonList.get(0));
        when(personMapper.convertToDTO(mockedPersonList.get(0))).thenReturn(mockedDTO);
        when(personRepository.save(any(Person.class))).thenReturn(mockedPersonList.get(0));

        RegisterPersonDTO resultDTO = personService.registerPerson(mockedDTO);

        verify(personRepository, times(1)).save(any(Person.class));
        assertEquals(mockedDTO, resultDTO);
    }

    @DisplayName("Test for get all person and return all person")
    @Test
    void getAllPerson() {
        when(personRepository.findAll()).thenReturn(mockedPersonList);
        List<Person> resultList = personService.getAllPerson();
        assertEquals(mockedPersonList.size(), resultList.size());
        verify(personRepository, times(1)).findAll();
    }

    @DisplayName("Test for find person By Id and return The Person")
    @Test
    void givenIdPersonAndReturnThePerson() {
        String personId = "321";
        when(personRepository.findById(personId)).thenReturn(Optional.of(mockedPersonList.get(0)));
        Person resultPerson = personService.findPersonById(personId);
        verify(personRepository, times(1)).findById(personId);
        assertEquals(mockedPersonList.get(0), resultPerson);
    }

    @DisplayName("Test for find person By Id and return Person Not Find")
    @Test
    void givenIdPersonAndReturnPersonNotFind() {
        String personId = "321";
        when(personRepository.findById(personId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            personService.findPersonById(personId);
        });
    }

    @DisplayName("Test for update Person with existing Id")
    @Test
    void givenIdPersonAndUpdatePerson() {
        String id = "321";
        RegisterPersonDTO updatedPerson = new RegisterPersonDTO("updatedJohn", "438-111-1111", "updatedjohn@gmail.com", new AddressDTO("rue des johns",
                "3", "j4k4j4", "Saint Jean Sur Richelieu", "Canada"));

        when(personRepository.findById(id)).thenReturn(Optional.of(mockedPersonList.get(0)));
        when(personRepository.save(Mockito.any())).thenReturn(mockedPersonList.get(0));
        RegisterPersonDTO resultDTO = personService.updatePerson(id, updatedPerson);

        verify(personRepository, times(1)).findById(id);
        verify(personRepository, times(1)).save(any(Person.class));

        assertEquals("updatedJohn", resultDTO.name());
        assertEquals("438-111-1111", resultDTO.telephone());
        assertEquals("updatedjohn@gmail.com", resultDTO.email());
        assertEquals("rue des johns", resultDTO.address().street());
        assertEquals("3", resultDTO.address().complement());
        assertEquals("j4k4j4", resultDTO.address().postalCode());
        assertEquals("Saint Jean Sur Richelieu", resultDTO.address().city());
        assertEquals("Canada", resultDTO.address().pays());
    }

    @DisplayName("Test for find person By Id and delete this Person")
    @Test
    void givenIdPersonAndDeleteThisPerson() {
        String personId = "321";
        when(personRepository.findById(personId)).thenReturn(Optional.of(mockedPersonList.get(0)));
        personService.deletePerson(personId);
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(1)).deleteById(personId);

    }

    @DisplayName("Test for find person By Id and return Person Not Find")
    @Test
    void givenIdPersonAndReturnPersonNotFindButNotDelete() {
        String personId = "321";
        when(personRepository.findById(personId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            personService.findPersonById(personId);
        });
    }
}