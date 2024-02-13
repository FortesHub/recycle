
package com.recycle.recycle.controller;

import com.recycle.recycle.domain.*;
import com.recycle.recycle.dto.AddressCompositeDTO;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.dto.CompanyDTO;
import com.recycle.recycle.dto.ExceptionDTO;
//import com.recycle.recycle.service.CompanyService;
import com.recycle.recycle.service.CompanyService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyControllerTest {
    @InjectMocks
    private CompanyController companyController;
    @Mock
    private CompanyService companyService;
    private CompanyDTO companyDTO;
    private List<Company> companyList;
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
    private List<Establishment> establishments;
    private List<String> establishmentsString;
    private String companyId;
    private CompanyDTO updatedcompanyDTO;
    private String notFound = "Company Not Found!";
    private String alreadyExist = "Company Already Exist";
    private String deleted = "Company deleted successfully!";

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
        establishments = Arrays.asList(new Establishment("establishmentId123", "Walamart", "4444", "walmart@gmail.com", address, employees, machines));
        establishmentsString = Arrays.asList("establishmentId123");
        companyList = Arrays.asList(
                new Company("companyId1", "Company1", "123456789",
                        "company@example.com", address, employees, machines, establishments));
        companyDTO = new CompanyDTO(
                "Company1", "123456789",
                "company@example.com", addressDTO, employeesString, establishmentsString, machinesString);
         companyId = "companyId1";
         updatedcompanyDTO = new CompanyDTO(
                "Company2",
                "123456789",
                "company@example.com",
                addressDTO, employeesString, establishmentsString, machinesString);
    }

    @DisplayName("Test for registerCompany and return CREATED company")
    @Test
    void whenRegisterCompanyReturnCreated(){
        when(companyService.registerCompany(any())).thenReturn(companyDTO);
        ResponseEntity<?> response = companyController.registerCompany(companyDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(companyDTO, response.getBody());
        verify(companyService, times(1)).registerCompany(companyDTO);
    }

    @DisplayName("Test to registerCompany and return Company Already Exist")
    @Test
    void whenRegisterCompanyRetunsAlreadyExist(){
        when(companyService.registerCompany(any())).thenThrow(new DataIntegrityViolationException(alreadyExist));
        ResponseEntity<?> response = companyController.registerCompany(companyDTO);
        ExceptionDTO exception = (ExceptionDTO) response.getBody();
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(alreadyExist, exception.message());
        verify(companyService, times(1)).registerCompany(companyDTO);
    }

    @DisplayName("Test for Get All Companies and return List of Company")
    @Test
    void whenGetAllCompanyReturnOk(){
        when(companyService.getAllCompany()).thenReturn(companyList);
        ResponseEntity<List<Company>> response = companyController.getAllCompany();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(companyList, response.getBody());
        verify(companyService, times(1)).getAllCompany();
    }

    @DisplayName("Test for Get Company ID and return Optional Company")
    @Test
    void whenGetCompanyByIdReturnOk(){
        when(companyService.getCompanyById(any())).thenReturn(Optional.ofNullable((companyList.get(0))));
        ResponseEntity<?> response = companyController.getCompanyById(companyList.get(0).getCompanyId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals((companyList.get(0)), response.getBody());
        verify(companyService, times(1)).getCompanyById(companyList.get(0).getCompanyId());
    }

    @DisplayName("Test for Get Company ID and return Company not found")
    @Test
    void whenGetCompanyByIdReturnNotFound(){
        String companyId = "111";
        when(companyService.getCompanyById(companyId)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> companyController.getCompanyById(companyId));
        assertEquals(notFound, exception.getMessage());
        verify(companyService, times(1)).getCompanyById(companyId);
    }

    @DisplayName("Test for updateCompany and return Ok")
    @Test
    void whenUpdateCompanyReturnOk(){
        when(companyService.updateCompany(companyId, updatedcompanyDTO)).thenReturn(updatedcompanyDTO);
        ResponseEntity<?> response = companyController.updateCompany(companyId, updatedcompanyDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedcompanyDTO, response.getBody());
        verify(companyService, times(1)).updateCompany(companyId, updatedcompanyDTO);
    }

    @DisplayName("Test to UpdateCompany and return Company Id Not Found")
    @Test
    void whenUpdateCompanyThrowException(){
        when(companyService.updateCompany(companyId, updatedcompanyDTO)).thenThrow(new DataIntegrityViolationException(notFound));
        ResponseEntity<?> response = companyController.updateCompany(companyId, updatedcompanyDTO);
        ExceptionDTO exception = (ExceptionDTO) response.getBody();
        assertEquals(notFound, exception.message());
        assertEquals(((ExceptionDTO) response.getBody()).statusCode(), exception.statusCode());
        verify(companyService, times(1)).updateCompany(companyId, updatedcompanyDTO);
    }

    @DisplayName("Test for deleteCompany and return message = Company deleted successfully! ")
    @Test
    void whenDeleteCompanyByIdReturnOK(){
        when(companyService.deleteCompany(any())).thenReturn(true);
        ResponseEntity<?> response = companyController.deleteCompany(companyList.get(0).getCompanyId());
        assertEquals(deleted, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(companyService, times(1)).deleteCompany(companyList.get(0).getCompanyId());
    }

    @DisplayName("Test for deleteCompany not found exception ")
    @Test
    void whenDeleteCompanyByIdReturnNotFound(){
        when(companyService.deleteCompany(companyId)).thenReturn(false);
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> companyController.deleteCompany(companyId));
                assertEquals(notFound, exception.getMessage());
                verify(companyService, times(1)).deleteCompany(companyId);
    }
}