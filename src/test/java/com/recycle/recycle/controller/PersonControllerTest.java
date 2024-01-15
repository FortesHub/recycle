package com.recycle.recycle.controller;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.domain.AddressComposite;
import com.recycle.recycle.domain.Company;
import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.AddressCompositeDTO;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.dto.ExceptionDTO;
import com.recycle.recycle.dto.PersonDTO;
import com.recycle.recycle.infra.GlobalControllerAdvice;
import com.recycle.recycle.mapper.PersonMapper;
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
    private List<Company> companies;
    private List<Address> addressList;
    private String personNotFound = "Person Not Found!";
    private String personAlreadyExist = "Person Already Exist";
    private String pesonDeleted = "Person deleted successfully!";

    @BeforeEach
    public void setUp() {
        addressList = Arrays.asList(
                new Address(new AddressComposite("rue des johns", "3", "j4k4j4"),
                        "Saint Jean Sur Richelieu", "Canada"),
                new Address(new AddressComposite("rue des Brin", "4", "j4k4j4"),
                        "Saint Jean Sur Richelieu", "Canada"));
        personDTO = new PersonDTO("John Doe", "123456789", "john.doe@example.com",
                new AddressDTO(new AddressCompositeDTO("rue des johns", "3", "j4k4j4"),
                        "Saint Jean Sur Richelieu", "Canada"), List.of("companyId1", "companyId2"));
        personList = Arrays.asList(
                new Person("personId123", "John Doe", "123456789",
                        "john.doe@example.com", addressList.get(0), List.of()));
        companies = Arrays.asList(
                new Company("companyId1", "Company1", "123456789",
                        "company@example.com", addressList.get(0), List.of()));

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
        when(personService.registerPerson(personDTO)).thenThrow(new DataIntegrityViolationException(personAlreadyExist));
        ResponseEntity<?> response = personController.registerPerson(personDTO);
        ExceptionDTO exceptionDTO = (ExceptionDTO) response.getBody();
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(personAlreadyExist, exceptionDTO.message());
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
        when(personService.getPersonById(any())).thenReturn(Optional.ofNullable(personList.get(0)));
        ResponseEntity<?> response = personController.getPersonById(personList.get(0).getPersonId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Optional<Person> personOptional = (Optional<Person>) response.getBody();
        assertTrue(personOptional.isPresent());
        assertEquals(personList.get(0), personOptional.get());
        verify(personService, times(1)).getPersonById(personList.get(0).getPersonId());
    }

    @DisplayName("Test for Get person ID and return Person not found")
    @Test
    void whenGetPersonByIdReturnNotFound() {
        String personId = "999";
        when(personService.getPersonById(personId)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> personController.getPersonById(personId));
        assertEquals(personNotFound, exception.getMessage());
        verify(personService, times(1)).getPersonById(personId);
    }

    @DisplayName("Test for updatePerson and return Ok ")
    @Test
    void whenUpdatePersonReturnOk() {
        String personId = "321";
        PersonDTO updatedPersonDTO = new PersonDTO("updatedJohn", "438-111-1111", "updatedjohn@gmail.com",new AddressDTO(new AddressCompositeDTO("rue des Pines", "3", "j4k4j4"),
                "Saint Jean Sur Richelieu", "Canada"), List.of("companyId1"));
        when(personService.updatePerson(personId, personDTO)).thenReturn(updatedPersonDTO);
        ResponseEntity<?> response = personController.updatePerson(personId, personDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(personService, times(1)).updatePerson(personId, personDTO);
    }

    @DisplayName("Test to UpdatePerson and return Person Id Not Found")
    @Test
    void whenUpdatePersonThrowException() {
        String personId = "321";
        PersonDTO updatedPersonDTO = new PersonDTO("updatedJohn", "438-111-1111", "updatedjohn@gmail.com",new AddressDTO(new AddressCompositeDTO("rue des Pines", "3", "j4k4j4"),
                "Saint Jean Sur Richelieu", "Canada"), List.of("companyId1"));
        when(personService.updatePerson(personId, personDTO)).thenThrow(new DataIntegrityViolationException(personNotFound));
        ResponseEntity<?> response = personController.updatePerson(personId, personDTO);
        ExceptionDTO exception = (ExceptionDTO) response.getBody();
        assertEquals(((ExceptionDTO) response.getBody()).message(), personNotFound);
        assertEquals(((ExceptionDTO) response.getBody()).statusCode(), exception.statusCode());
        verify(personService, times(1)).updatePerson(personId, personDTO);
    }

    @DisplayName("Test for deletePerson and return message = Person deleted successfully! ")
    @Test
    void whenDeletePersonByIdReturnOK() {
        when(personService.deletePerson(any(String.class))).thenReturn(true);
        ResponseEntity<?> response = personController.deletePerson(personList.get(0).getPersonId());
        assertEquals(pesonDeleted, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(personService, times(1)).deletePerson(personList.get(0).getPersonId());
    }

    @DisplayName("Test for deletePerson not found exception ")
    @Test
    void whenDeletePersonByIdReturnNotFound() {
        String personId = "999";
        when(personService.deletePerson(any(String.class))).thenReturn(false);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> personController.deletePerson(personId));
        assertEquals(personNotFound, exception.getMessage());
        verify(personService, times(1)).deletePerson(personId);
    }
}




