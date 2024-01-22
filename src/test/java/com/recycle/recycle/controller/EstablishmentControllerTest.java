package com.recycle.recycle.controller;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.domain.AddressComposite;
import com.recycle.recycle.domain.Establishment;
import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.AddressCompositeDTO;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.dto.EstablishmentDTO;
import com.recycle.recycle.dto.ExceptionDTO;
import com.recycle.recycle.service.EstablishmentService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.objectweb.asm.TypeReference;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstablishmentControllerTest {
    @InjectMocks
    EstablishmentController establishmentController;
    @Mock
    EstablishmentService establishmentService;
    private EstablishmentDTO establishmentDTO;
    private AddressComposite addressComposite;
    private AddressCompositeDTO addressCompositeDTO;
    private Address address;
    private AddressDTO addressDTO;
    private List<Person> employees;
    private List<Establishment> establishments;
    private String notFound = "establishment Not Found!";
    private String alreadyExist = "establishment Already Exist";
    private String deleted = "establishment deleted successfully!";
    private String establishmentId = "companyId1";

    @BeforeEach
    void setUp() {
        addressComposite = new AddressComposite("rue des johns", "3", "j4k4j4");
        address = new Address(addressComposite, "Saint Jean Sur Richelieu", "Canada");
        addressCompositeDTO = new AddressCompositeDTO("rue des johns", "3", "j4k4j4");
        addressDTO = new AddressDTO(addressCompositeDTO, "Saint Jean Sur Richelieu", "Canada");
        employees = Arrays.asList(
                new Person("personId123", "John Doe", "123456789",
                        "john.doe@example.com", address, List.of(), List.of())
        );
        establishments = Arrays.asList(
                new Establishment("123", "establishment", "222", "uniprix@uniprix", address, employees)
        );
        establishmentDTO = new EstablishmentDTO("establishment", "222", "uniprix@uniprix", addressDTO, employees);
    }

    @DisplayName("Test to register Establishment and return CREATED")
    @Test
    void whenRegisterEstablishmentRturnCreated() {
        when(establishmentService.registerEstablishment(establishmentDTO)).thenReturn(establishmentDTO);
        ResponseEntity<?> response = establishmentController.registerEstablishment(establishmentDTO);
        verify(establishmentService, times(1)).registerEstablishment(establishmentDTO);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(response.getBody(), establishmentDTO);
    }

    @DisplayName("Test to register Establishment and return AlreadyExist")
    @Test
    void whenRregisterEstablishmentReturnAlreadyExist() {
        when(establishmentService.registerEstablishment(establishmentDTO)).thenThrow(new DataIntegrityViolationException(alreadyExist));
        ResponseEntity<?> response = establishmentController.registerEstablishment(establishmentDTO);
        ExceptionDTO exceptionDTO = new ExceptionDTO(alreadyExist, response.getStatusCode().value());
        verify(establishmentService, times(1)).registerEstablishment(establishmentDTO);
        assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
        assertEquals(exceptionDTO.message(), alreadyExist);
    }

    @DisplayName("Test to get list of Establishment and return ok")
    @Test
    void whenGetAllEstablishmentReturnOk() {
        when(establishmentService.getAllEstablishment()).thenReturn(establishments);
        ResponseEntity<List<Establishment>> response = establishmentController.getAllEstablishment();
        verify(establishmentService, times(1)).getAllEstablishment();
        assertEquals(response.getBody(), establishments);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @DisplayName("Test get Establishment by Id and return ok")
    @Test
    void whenGetEstablishmentByIdReturnOK() {
        when(establishmentService.getEstablishmentByID(establishmentId)).thenReturn(Optional.of(establishments.get(0)));
        ResponseEntity<?> response = establishmentController.getEstablishmentById(establishmentId);
        verify(establishmentService, times(1)).getEstablishmentByID(establishmentId);
        assertEquals(response.getBody(), Optional.of(establishments.get(0)));
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @DisplayName("Test get Establishment by Id and return Not Found")
    @Test
    void whenGetEstablishmentByIdReturnNotFound() {
        when(establishmentService.getEstablishmentByID(establishmentId)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> establishmentController.getEstablishmentById(establishmentId));
        verify(establishmentService, times(1)).getEstablishmentByID(establishmentId);
        assertEquals(exception.getMessage(), notFound);
    }

    @DisplayName("Test update Establishment and return ok")
    @Test
    void whenUpdateEstablishmentReturnOk() {
        when(establishmentService.updateEstablishment(establishmentId, establishmentDTO)).thenReturn(establishmentDTO);
        ResponseEntity<?> response = establishmentController.updateEstablishment(establishmentId, establishmentDTO);
        verify(establishmentService, times(1)).updateEstablishment(establishmentId, establishmentDTO);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), establishmentDTO);
    }

    @DisplayName("Test update Establishment and return Not Found")
    @Test
    void whenUpdateEstablishmentReturnNotFound() {
        when(establishmentService.updateEstablishment(establishmentId, establishmentDTO)).thenThrow(new DataIntegrityViolationException(notFound));
        ResponseEntity<?> response = establishmentController.updateEstablishment(establishmentId, establishmentDTO);
        ExceptionDTO exceptionDTO = new ExceptionDTO(notFound, response.getStatusCode().value());
        verify(establishmentService, times(1)).updateEstablishment(establishmentId, establishmentDTO);
        assertEquals(response.getStatusCode().value(), exceptionDTO.statusCode());
        assertEquals(notFound, exceptionDTO.message());
    }

    @DisplayName("Test delete Establishment by Id and establishment deleted")
    @Test
    void whenDeleteEstablishmentByIdreturnOk() {
        when(establishmentService.deleteEstablishment(establishmentId)).thenReturn(true);
        ResponseEntity<?> response = establishmentController.deleteEstablishment(establishmentId);
        verify(establishmentService, times(1)).deleteEstablishment(establishmentId);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), deleted);
    }

    @DisplayName("Test delete Establishment by Id and return notFound")
    @Test
    void whenDeleteEstablishmentByIdreturnNotFound() {
        when(establishmentService.deleteEstablishment(establishmentId)).thenReturn(false);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> establishmentController.deleteEstablishment(establishmentId));
        verify(establishmentService, times(1)).deleteEstablishment(establishmentId);
        assertEquals(exception.getMessage(), notFound);
    }
}