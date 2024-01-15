
package com.recycle.recycle.controller;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.domain.AddressComposite;
import com.recycle.recycle.domain.Company;
import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.AddressCompositeDTO;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.dto.CompanyDTO;
import com.recycle.recycle.dto.ExceptionDTO;
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
    private List<Person> personList;
    private List<Address> addressList;
    private List<Company> companyList;
    private CompanyDTO updatedCompanyDTO;
    private String companyNotFound = "Company Not Found!";
    private String companyAlreadyExist = "Company Already Exist";
    private String companyDeleted = "Company deleted successfully!";
    private String companyId = "companyId1";

    @BeforeEach
    void setUp() {
        addressList = Arrays.asList(
                new Address(new AddressComposite("rue des johns", "3", "j4k4j4"),
                        "Saint Jean Sur Richelieu", "Canada"));
        personList = Arrays.asList(
                new Person("personId123", "John Doe", "123456789",
                        "john.doe@example.com", addressList.get(0), List.of()));
        companyList = Arrays.asList(
                new Company("companyId1", "Company1", "123456789",
                        "company@example.com", addressList.get(0), List.of()));
        companyDTO = new CompanyDTO(
                "Company1",
                "123456789",
                "company@example.com",
                new AddressDTO(new AddressCompositeDTO("rue des johns", "3", "j4k4j4"),
                        "Saint Jean Sur Richelieu",
                        "Canada"), List.of());
        updatedCompanyDTO = new CompanyDTO(
                "UpdateCompany1",
                "123456789",
                "company@example.com",
                new AddressDTO(new AddressCompositeDTO("rue des johns", "3", "j4k4j4"),
                        "Saint Jean Sur Richelieu",
                        "Canada"), List.of());
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
        when(companyService.registerCompany(any())).thenThrow(new DataIntegrityViolationException(companyAlreadyExist));
        ResponseEntity<?> response = companyController.registerCompany(companyDTO);
        ExceptionDTO exception = (ExceptionDTO) response.getBody();
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(companyAlreadyExist, exception.message());
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
        when(companyService.getCompanyById(any())).thenReturn(Optional.ofNullable(companyList.get(0)));
        ResponseEntity<?> response = companyController.getCompanyById(companyList.get(0).getCompanyId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Optional.ofNullable(companyList.get(0)), response.getBody());
        verify(companyService, times(1)).getCompanyById(companyList.get(0).getCompanyId());
    }

    @DisplayName("Test for Get Company ID and return Company not found")
    @Test
    void whenGetCompanyByIdReturnNotFound(){
        String companyId = "111";
        when(companyService.getCompanyById(companyId)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> companyController.getCompanyById(companyId));
        assertEquals(companyNotFound, exception.getMessage());
        verify(companyService, times(1)).getCompanyById(companyId);
    }

    @DisplayName("Test for updateCompany and return Ok")
    @Test
    void whenUpdateCompanyReturnOk(){
        when(companyService.updateCompany(companyId, updatedCompanyDTO)).thenReturn(updatedCompanyDTO);
        ResponseEntity<?> response = companyController.updateCompany(companyId, updatedCompanyDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCompanyDTO, response.getBody());
        verify(companyService, times(1)).updateCompany(companyId, updatedCompanyDTO);
    }

    @DisplayName("Test to UpdateCompany and return Company Id Not Found")
    @Test
    void whenUpdateCompanyThrowException(){
        when(companyService.updateCompany(companyId, updatedCompanyDTO)).thenThrow(new DataIntegrityViolationException(companyNotFound));
        ResponseEntity<?> response = companyController.updateCompany(companyId, updatedCompanyDTO);
        ExceptionDTO exception = (ExceptionDTO) response.getBody();
        assertEquals(companyNotFound, exception.message());
        assertEquals(((ExceptionDTO) response.getBody()).statusCode(), exception.statusCode());
        verify(companyService, times(1)).updateCompany(companyId, updatedCompanyDTO);
    }

    @DisplayName("Test for deleteCompany and return message = Company deleted successfully! ")
    @Test
    void whenDeleteCompanyByIdReturnOK(){
        when(companyService.deleteCompany(any())).thenReturn(true);
        ResponseEntity<?> response = companyController.deleteCompany(companyList.get(0).getCompanyId());
        assertEquals(companyDeleted, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(companyService, times(1)).deleteCompany(companyList.get(0).getCompanyId());
    }

    @DisplayName("Test for deleteCompany not found exception ")
    @Test
    void whenDeleteCompanyByIdReturnNotFound(){
        when(companyService.deleteCompany(companyId)).thenReturn(false);
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> companyController.deleteCompany(companyId));
                assertEquals(companyNotFound, exception.getMessage());
                verify(companyService, times(1)).deleteCompany(companyId);
    }
}