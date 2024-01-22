package com.recycle.recycle.mapper;

import com.recycle.recycle.domain.*;
import com.recycle.recycle.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompanyMapperTest {
    private CompanyMapper companyMapper = Mappers.getMapper(CompanyMapper.class);
    private Company company;
    private CompanyDTO companyDTO;
    private AddressComposite addressComposite;
    private Address address;
    private AddressCompositeDTO addressCompositeDTO;
    private AddressDTO addressDTO;
    List<Person> employees;

    @BeforeEach
    public void setUp() {
        addressComposite = new AddressComposite("rue des johns", "3", "j4k4j4");
        address = new Address(addressComposite, "Saint Jean Sur Richelieu", "Canada");
        addressCompositeDTO = new AddressCompositeDTO("rue des johns", "3", "j4k4j4");
        addressDTO = new AddressDTO(addressCompositeDTO, "Saint Jean Sur Richelieu", "Canada");
        employees = Arrays.asList(
                new Person("personId123", "John Doe", "123456789",
                        "john.doe@example.com", address, List.of(), List.of())
        );
        companyDTO = new CompanyDTO("companyDTO", "222", "uniprix@uniprix", addressDTO, employees);
        company = new Company("123", "company", "222", "uniprix@uniprix", address, employees);
    }

    @Test
    void convertToDTO() {
        CompanyDTO companyDTO = companyMapper.convertToDTO(company);
        assertEquals(company.getName(), companyDTO.name());
    }

    @Test
    void convertToCompany() {
        Company company = companyMapper.convertToCompany(companyDTO);
        assertEquals(companyDTO.name(), company.getName());
    }
}