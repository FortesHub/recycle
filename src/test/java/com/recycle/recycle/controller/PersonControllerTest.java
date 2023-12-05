package com.recycle.recycle.controller;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.dto.ExceptionDTO;
import com.recycle.recycle.dto.PersonDTO;
import com.recycle.recycle.infra.GlobalControllerException;
import com.recycle.recycle.service.PersonService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {
    @InjectMocks
    private PersonController personController;
    @Mock
    private PersonService personService;
    @Spy
    private GlobalControllerException globalControllerException = new GlobalControllerException();
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

    @DisplayName("Test for registerPerson and return CREATED person")
    @Test
    void whenRegisterPersonReturnCreated() {
        when(personService.registerPerson(any())).thenReturn(personDTO);
        ResponseEntity<PersonDTO> response = personController.registerPerson(personDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(personDTO, response.getBody());
        verify(personService, times(1)).registerPerson(Mockito.any());
    }

    @DisplayName("Test for Get All Person and return List of person")
    @Test
    void whenGetAllPersonReturnOk() {
        when(personService.getAllPerson()).thenReturn(personList);
        ResponseEntity<List<Person>> response = personController.getAllPersons();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personList, response.getBody());
        verify(personService, times(1)).getAllPerson();
    }

    @DisplayName("Test for Get person ID and return Optional Person")
    @Test
    void whenGetPersonByIdReturnOk() {
        when(personService.getPersonById(any())).thenReturn(Optional.ofNullable(personList.get(0)));
        ResponseEntity<?> response = personController.getPersonById(personList.get(0).getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Optional<Person> personOptional = (Optional<Person>) response.getBody();
        assertTrue(personOptional.isPresent());
        assertEquals(personList.get(0), personOptional.get());
        verify(personService, times(1)).getPersonById(personList.get(0).getId());
    }

    @DisplayName("Test for Get person ID and return Person not found")
    @Test
    void whenGetPersonByIdReturnNotFound() {
        String personId = "999";
        when(personService.getPersonById(personId)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> personController.getPersonById(personId));
        ResponseEntity<ExceptionDTO> response = globalControllerException.handleEntityNotFoundException(exception);
        assertEquals("Person Not Found", response.getBody().message());
        verify(personService, times(1)).getPersonById(personId);
    }

    @DisplayName("Test for update Person and return Ok ")

    @Test
    void whenUpdatePersonReturnOk() {
        String personId = "321";
        PersonDTO updatedPersonDTO = new PersonDTO("updatedJohn", "438-111-1111", "updatedjohn@gmail.com", new AddressDTO("rue des johns",
                "3", "j4k4j4", "Saint Jean Sur Richelieu", "Canada"));
        when(personService.updatePerson(personId, updatedPersonDTO)).thenReturn(updatedPersonDTO);
        ResponseEntity<PersonDTO> response = personController.updatePerson(personId, updatedPersonDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(personService, times(1)).updatePerson(personId, updatedPersonDTO);
    }

    @DisplayName("Test for deletePerson and return message = Person deleted successfully! ")
    @Test
    void whenDeletePersonByIdReturnOK() {
        when(personService.deletePerson(any(String.class))).thenReturn(true);
        ResponseEntity<?> response = personController.deletePerson(personList.get(0).getId());
        assertEquals("Person deleted successfully!", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(personService, times(1)).deletePerson(personList.get(0).getId());
    }

    @DisplayName("Test for deletePerson not found exception ")
    @Test
    void whenDeletePersonByIdReturnNotFound() {
        String personId = "999";
        when(personService.deletePerson(any(String.class))).thenReturn(false);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> personController.deletePerson(personId));
        ResponseEntity<ExceptionDTO> response = globalControllerException.handleEntityNotFoundException(exception);
        assertEquals("Person Not Found", response.getBody().message());
        verify(personService, times(1)).deletePerson(personId);
    }
}




