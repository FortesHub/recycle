package com.recycle.recycle.controller;

import com.recycle.recycle.domain.*;
import com.recycle.recycle.dto.AddressCompositeDTO;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.dto.ExceptionDTO;
import com.recycle.recycle.dto.PersonDTO;
import com.recycle.recycle.infra.GlobalControllerAdvice;
//import com.recycle.recycle.mapper.PersonMapper;
//import com.recycle.recycle.service.PersonService;
import com.recycle.recycle.service.PersonService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
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
    private PersonDTO personDTO;
    private List<Person> personList;
    private String notFound = "Person Not Found!";
    private String alreadyExist = "Person Already Exist";
    private String deleted = "Person deleted successfully!";

    @BeforeEach
    public void setUp() {
        personDTO = new PersonDTO("John Doe", "123456789", "john.doe@example.com");
        personList = Arrays.asList(
                new Person("personId123", "John Doe", "123456789",
                        "john.doe@example.com"));

    }

    @DisplayName("Test for registerPerson and return CREATED person")
    @Test
    void whenRegisterPersonReturnCreated() {
        when(personService.registerPerson(any())).thenReturn(personDTO);
        ResponseEntity<?> response = personController.registerPerson(personDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(personDTO, response.getBody());
        verify(personService, times(1)).registerPerson(personDTO);
    }

    @DisplayName("Test to registerPerson and return Person Already Exist")
    @Test
    void whenRegisterPersonRetunsAlreadyExist() {
        when(personService.registerPerson(personDTO)).thenThrow(new DataIntegrityViolationException(alreadyExist));
        ResponseEntity<?> response = personController.registerPerson(personDTO);
        ExceptionDTO exceptionDTO = (ExceptionDTO) response.getBody();
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(alreadyExist, exceptionDTO.message());
        verify(personService, times(1)).registerPerson(personDTO);
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
        when(personService.getPersonById(any())).thenReturn(Optional.of(personList.get(0)));
        ResponseEntity<?> response = personController.getPersonById(personList.get(0).getPersonId());
        verify(personService, times(1)).getPersonById(personList.get(0).getPersonId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personList.get(0), response.getBody());

    }

    @DisplayName("Test for Get person ID and return Person not found")
    @Test
    void whenGetPersonByIdReturnNotFound() {
        String personId = "999";
        when(personService.getPersonById(personId)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> personController.getPersonById(personId));
        assertEquals(notFound, exception.getMessage());
        verify(personService, times(1)).getPersonById(personId);
    }

    @DisplayName("Test for updatePerson and return Ok ")
    @Test
    void whenUpdatePersonReturnOk() {
        PersonDTO updatedPersonDTO = new PersonDTO("John Doee", "123456789", "john.doee@example.com");
        when(personService.updatePerson(personList.get(0).getPersonId(), personDTO)).thenReturn(updatedPersonDTO);
        ResponseEntity<?> response = personController.updatePerson(personList.get(0).getPersonId(), personDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(personService, times(1)).updatePerson(personList.get(0).getPersonId(), personDTO);
    }

    @DisplayName("Test to UpdatePerson and return Person Id Not Found")
    @Test
    void whenUpdatePersonThrowException() {
        PersonDTO updatedPersonDTO = new PersonDTO("John Doee", "123456789", "john.doee@example.com");
        when(personService.updatePerson(personList.get(0).getPersonId(), personDTO)).thenThrow(new DataIntegrityViolationException(notFound));
        ResponseEntity<?> response = personController.updatePerson(personList.get(0).getPersonId(), personDTO);
        ExceptionDTO exception = (ExceptionDTO) response.getBody();
        assertEquals(((ExceptionDTO) response.getBody()).message(), notFound);
        assertEquals(((ExceptionDTO) response.getBody()).statusCode(), exception.statusCode());
        verify(personService, times(1)).updatePerson(personList.get(0).getPersonId(), personDTO);
    }

    @DisplayName("Test for deletePerson and return message = Person deleted successfully! ")
    @Test
    void whenDeletePersonByIdReturnOK() {
        when(personService.deletePerson(any(String.class))).thenReturn(true);
        ResponseEntity<?> response = personController.deletePerson(personList.get(0).getPersonId());
        assertEquals(deleted, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(personService, times(1)).deletePerson(personList.get(0).getPersonId());
    }

    @DisplayName("Test for deletePerson not found exception ")
    @Test
    void whenDeletePersonByIdReturnNotFound() {
        String personId = "999";
        when(personService.deletePerson(any(String.class))).thenReturn(false);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> personController.deletePerson(personId));
        assertEquals(notFound, exception.getMessage());
        verify(personService, times(1)).deletePerson(personId);
    }
}




