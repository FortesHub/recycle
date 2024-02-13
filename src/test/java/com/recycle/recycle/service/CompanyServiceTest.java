package com.recycle.recycle.service;

import com.recycle.recycle.domain.*;
import com.recycle.recycle.dto.AddressCompositeDTO;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.dto.CompanyDTO;
//import com.recycle.recycle.mapper.CompanyMapper;
import com.recycle.recycle.mapper.CompanyMapper;
import com.recycle.recycle.repository.CompanyRepository;
import com.recycle.recycle.service.utils.AddressUtils;
import com.recycle.recycle.service.utils.EstablishmentUtils;
import com.recycle.recycle.service.utils.MachineUtils;
import com.recycle.recycle.service.utils.PersonUtils;
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
    AddressUtils addressUtils;
    @Mock
    PersonUtils personUtils;
    @Mock
    EstablishmentUtils establishmentUtils;
    @Mock
    MachineUtils machineUtils;
    @Spy
    CompanyMapper companyMapper = Mappers.getMapper(CompanyMapper.class);
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
    }

    @DisplayName("Test for register Company and return Company")
    @Test
    void givenCompanyWhenGetCompany() {
        when(addressUtils.getAddress(any())).thenReturn(address);
        when(personUtils.getEmployees(any())).thenReturn(employees);
        when(establishmentUtils.getEstablishments(any())).thenReturn(establishments);
        when(machineUtils.getMachines(any())).thenReturn(machines);
        when(companyRepository.save(any(Company.class))).thenReturn(companyList.get(0));
        CompanyDTO registeredCompany = companyService.registerCompany(companyDTO);
        verify(companyRepository, times(1)).save(any(Company.class));
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
                addressDTO, employeesString, establishmentsString, machinesString);
        Company companydtoToCompany = new Company("companyId1", "Company2", "123456789",
                "company@example.com", address, employees, machines, establishments);
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