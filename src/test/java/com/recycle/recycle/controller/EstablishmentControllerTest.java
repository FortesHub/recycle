package com.recycle.recycle.controller;

import com.recycle.recycle.domain.*;
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
    private List<Establishment> establishments;
    private List<Person> employees;
    private List<String> employeesString;
    private Address address;
    private AddressComposite addressComposite;
    private AddressCompositeDTO addressCompositeDTO;
    private AddressDTO addressDTO;
    private List<Machine> machines;
    private List<String> machinesString;
    private List<Material> containers;
    private Status status;
    private String notFound = "establishment Not Found!";
    private String alreadyExist = "establishment Already Exist";
    private String deleted = "establishment deleted successfully!";


    @BeforeEach
    void setUp() {
        addressComposite = new AddressComposite("rue des johns", "3", "j4k4j4");
        address = new Address(addressComposite, "Saint Jean Sur Richelieu", "Canada");
        addressCompositeDTO = new AddressCompositeDTO("rue des johns", "3", "j4k4j4");
        addressDTO = new AddressDTO(addressCompositeDTO, "Saint Jean Sur Richelieu", "Canada");
        employees = Arrays.asList(new Person("personId123", "John Doe", "123456789",
                "john.doe@example.com"));
        employeesString = Arrays.asList("personId123");
        status = Status.WORK;
        containers = Arrays.asList(new Material("materialId123", "paper"));
        machines = Arrays.asList(new Machine("machineId123", status, false, containers));
        machinesString = Arrays.asList("machineId123");
        establishments = Arrays.asList(
                new Establishment("123", "establishment", "222", "uniprix@uniprix", address, employees, machines)
        );
        establishmentDTO = new EstablishmentDTO("establishment", "222", "uniprix@uniprix", addressDTO, employeesString, machinesString);
    }

    @DisplayName("Test to register Establishment and return CREATED")
    @Test
    void whenRegisterEstablishmentRturnCreated() {
        when(establishmentService.registerEstablishment(establishmentDTO)).thenReturn(establishmentDTO);
        ResponseEntity<?> response = establishmentController.registerEstablishment(establishmentDTO);
        verify(establishmentService, times(1)).registerEstablishment(establishmentDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(establishmentDTO, response.getBody());
    }

    @DisplayName("Test to register Establishment and return AlreadyExist")
    @Test
    void whenRregisterEstablishmentReturnAlreadyExist() {
        when(establishmentService.registerEstablishment(establishmentDTO)).thenThrow(new DataIntegrityViolationException(alreadyExist));
        ResponseEntity<?> response = establishmentController.registerEstablishment(establishmentDTO);
        ExceptionDTO exceptionDTO = new ExceptionDTO(alreadyExist, response.getStatusCode().value());
        verify(establishmentService, times(1)).registerEstablishment(establishmentDTO);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(alreadyExist, exceptionDTO.message());
    }

    @DisplayName("Test to get list of Establishment and return ok")
    @Test
    void whenGetAllEstablishmentReturnOk() {
        when(establishmentService.getAllEstablishment()).thenReturn(establishments);
        ResponseEntity<List<Establishment>> response = establishmentController.getAllEstablishment();
        verify(establishmentService, times(1)).getAllEstablishment();
        assertEquals(establishments, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("Test to get list of Establishment and Return Ok by Filter")
    @Test
    void whenGetFiltersAndReturnListEstablishment(){
        when(establishmentService.getEstablishmentByFilters(establishments.get(0).getName(),
                establishments.get(0).getAddress().getAddressComposite().getPostalCode(),
                establishments.get(0).getMachines().get(0).getContainers().get(0).getType())).thenReturn(establishments);
        ResponseEntity<List<Establishment>> response = (ResponseEntity<List<Establishment>>) establishmentController.getEstablishmentByFilters(establishments.get(0).getName(),
                establishments.get(0).getAddress().getAddressComposite().getPostalCode(),
                establishments.get(0).getMachines().get(0).getContainers().get(0).getType());
        verify(establishmentService, times(1)).getEstablishmentByFilters(establishments.get(0).getName(),
                establishments.get(0).getAddress().getAddressComposite().getPostalCode(),
                establishments.get(0).getMachines().get(0).getContainers().get(0).getType());
        assertEquals(HttpStatus.OK, response.getStatusCode() );
        assertEquals(establishments, response.getBody());
    }

    @DisplayName("Test get Establishment by Id and return ok")
    @Test
    void whenGetEstablishmentByIdReturnOK() {
        when(establishmentService.getEstablishmentByID(establishments.get(0).getEstablishmentId())).thenReturn(Optional.of(establishments.get(0)));
        ResponseEntity<?> response = establishmentController.getEstablishmentById(establishments.get(0).getEstablishmentId());
        verify(establishmentService, times(1)).getEstablishmentByID(establishments.get(0).getEstablishmentId());
        assertEquals(establishments.get(0), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("Test get Establishment by Id and return Not Found")
    @Test
    void whenGetEstablishmentByIdReturnNotFound() {
        when(establishmentService.getEstablishmentByID(establishments.get(0).getEstablishmentId())).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> establishmentController.getEstablishmentById(establishments.get(0).getEstablishmentId()));
        verify(establishmentService, times(1)).getEstablishmentByID(establishments.get(0).getEstablishmentId());
        assertEquals(notFound, exception.getMessage() );
    }

    @DisplayName("Test update Establishment and return ok")
    @Test
    void whenUpdateEstablishmentReturnOk() {
        when(establishmentService.updateEstablishment(establishments.get(0).getEstablishmentId(), establishmentDTO)).thenReturn(establishmentDTO);
        ResponseEntity<?> response = establishmentController.updateEstablishment(establishments.get(0).getEstablishmentId(), establishmentDTO);
        verify(establishmentService, times(1)).updateEstablishment(establishments.get(0).getEstablishmentId(), establishmentDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(establishmentDTO, response.getBody());
    }

    @DisplayName("Test update Establishment and return Not Found")
    @Test
    void whenUpdateEstablishmentReturnNotFound() {
        when(establishmentService.updateEstablishment(establishments.get(0).getEstablishmentId(), establishmentDTO)).thenThrow(new DataIntegrityViolationException(notFound));
        ResponseEntity<?> response = establishmentController.updateEstablishment(establishments.get(0).getEstablishmentId(), establishmentDTO);
        ExceptionDTO exceptionDTO = new ExceptionDTO(notFound, response.getStatusCode().value());
        verify(establishmentService, times(1)).updateEstablishment(establishments.get(0).getEstablishmentId(), establishmentDTO);
        assertEquals(response.getStatusCode().value(), exceptionDTO.statusCode());
        assertEquals(notFound, exceptionDTO.message());
    }

    @DisplayName("Test delete Establishment by Id and establishment deleted")
    @Test
    void whenDeleteEstablishmentByIdreturnOk() {
        when(establishmentService.deleteEstablishment(establishments.get(0).getEstablishmentId())).thenReturn(true);
        ResponseEntity<?> response = establishmentController.deleteEstablishment(establishments.get(0).getEstablishmentId());
        verify(establishmentService, times(1)).deleteEstablishment(establishments.get(0).getEstablishmentId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deleted, response.getBody());
    }

    @DisplayName("Test delete Establishment by Id and return notFound")
    @Test
    void whenDeleteEstablishmentByIdreturnNotFound() {
        when(establishmentService.deleteEstablishment(establishments.get(0).getEstablishmentId())).thenReturn(false);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> establishmentController.deleteEstablishment(establishments.get(0).getEstablishmentId()));
        verify(establishmentService, times(1)).deleteEstablishment(establishments.get(0).getEstablishmentId());
        assertEquals(notFound, exception.getMessage());
    }
}