package com.recycle.recycle.service;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.domain.AddressComposite;
import com.recycle.recycle.domain.Company;
import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.AddressCompositeDTO;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.dto.CompanyDTO;
import com.recycle.recycle.dto.PersonDTO;
import com.recycle.recycle.mapper.CompanyMapper;
import com.recycle.recycle.repository.AddressRepository;
import com.recycle.recycle.repository.CompanyRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {
    @InjectMocks
    CompanyService companyService;
    @Mock
    CompanyRepository companyRepository;
    @Mock
    AddressRepository addressRepository;
    @Spy
    CompanyMapper companyMapper = Mappers.getMapper(CompanyMapper.class);
    CompanyDTO companyDTO;
    List<Person> personList;
    List<Address> addressList;
    List<Company> companyList;

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
    }

    @DisplayName("Test for register Company and return Company")
    @Test
    void givenCompanyWhenGetCompany() {
        when(addressRepository.findByAddressComposite(any())).thenReturn(addressList.get(0));
        when(companyRepository.save(any(Company.class))).thenReturn(companyList.get(0));
        CompanyDTO registeredCompany = companyService.registerCompany(companyDTO);
        verify(companyRepository, times(1)).save(any(Company.class));
        verify(addressRepository, times(1)).findByAddressComposite(addressList.get(0).getAddressComposite());
        assertEquals(companyDTO, registeredCompany);
    }

    @DisplayName("Test for Get All Companies and return List of Company")
    @Test
    void givenListCompanyWhenGetAllCompany() {
        when(companyRepository.findAll()).thenReturn(companyList);
        List<Company> resultCompany = companyService.getAllCompany();
        assertEquals(companyList, resultCompany);
        verify(companyRepository, times(1)).findAll();
    }

    @DisplayName("Test for get company by id and return company")
    @Test
    void givenCompanyWhenGetCompanyById() {
        when(companyRepository.findById(companyList.get(0).getCompanyId())).thenReturn(Optional.of(companyList.get(0)));
        Optional<Company> resultCompany = companyService.getCompanyById(companyList.get(0).getCompanyId());
        assertEquals(companyList.get(0).getCompanyId(), resultCompany.get().getCompanyId());
        verify(companyRepository, times(1)).findById(companyList.get(0).getCompanyId());
    }

    @DisplayName("Test for Update Company and return Company Updated")
    @Test
    void givenUpdatedCompanyWhenUpdateCompany() {
        String companyId = "companyId1";
        CompanyDTO updatedcompanyDTO = new CompanyDTO(
                "Company2",
                "123456789",
                "company@example.com",
                new AddressDTO(new AddressCompositeDTO("rue des johns", "3", "j4k4j4"),
                        "Saint Jean Sur Richelieu",
                        "Canada"), List.of());
        Company companydtoToCompany = new Company("companyId1","Company2", "123456789",
                "company@example.com", addressList.get(0), List.of());
        when(companyRepository.save(any(Company.class))).thenReturn(companydtoToCompany);
        CompanyDTO resultCompany = companyService.updateCompany(companyId, companyDTO);
              verify(companyRepository, times(1)).save(companyList.get(0));
        assertEquals("Company2", resultCompany.name());
    }

    @DisplayName("Test fot Delete Person and Return Person deleted!")
    @Test
    void givenPersonDeletedWhenGetCompanyId() {
        when(companyRepository.findById(companyList.get(0).getCompanyId())).thenReturn(Optional.ofNullable(companyList.get(0)));
        boolean resultCompany = companyService.deleteCompany(companyList.get(0).getCompanyId());
        verify(companyRepository, times(1)).findById(companyList.get(0).getCompanyId());
        verify(companyRepository, times(1)).delete(companyList.get(0));
        assertTrue(resultCompany);
    }
}