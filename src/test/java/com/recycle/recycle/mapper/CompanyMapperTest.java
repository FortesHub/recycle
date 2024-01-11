package com.recycle.recycle.mapper;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.domain.Company;
import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.dto.CompanyDTO;
import com.recycle.recycle.dto.PersonDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompanyMapperTest {
//    private CompanyMapper companyMapper = Mappers.getMapper(CompanyMapper.class);
//    private Address address = new Address(321L, "rue des johns", "3", "j4k4j4", "Saint Jean Sur Richelieu", "Canada");
//    private Person person = new Person("321", "John", "438-111-1111", "john@john.ca", address);
//    private PersonDTO personDTO = new PersonDTO(
//            "John",
//            "438-111-1111",
//            "john@john.ca",
//            new AddressDTO("rue des johns",
//                    "3",
//                    "j4k4j4",
//                    "Saint Jean Sur Richelieu",
//                    "Canada")
//    );
//
//    @Test
//    void convertToDTO() {
//        Company company = new Company();
//        company.setPerson(person);
//        CompanyDTO companyDTO = companyMapper.convertToDTO(company);
//        assertEquals(company.getPerson().getName(), companyDTO.person().name());
//    }
//
//    @Test
//    void convertToCompany() {
//        CompanyDTO companyDTO = new CompanyDTO(personDTO);
//        Company company = companyMapper.convertToCompany(companyDTO);
//        assertEquals(companyDTO.person().name(), company.getPerson().getName());
//    }
//
//    @Test
//    void updateDTOtoCompany() {
//        Address updateAddress = new Address(321L, "rue des johns", "3", "j4k4j4", "Saint Jean Sur Richelieu", "Canada");
//        Person updatePerson = new Person("321", "Maria", "438-111-1111", "john@john.ca", updateAddress);
//        Company company = new Company();
//        company.setPerson(updatePerson);
//        CompanyDTO companyDTO = new CompanyDTO(personDTO);
//        companyMapper.updateDTOtoCompany(companyDTO, company);
//    }
}