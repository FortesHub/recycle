package com.recycle.recycle.mapper;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.domain.AddressComposite;
import com.recycle.recycle.domain.Company;
import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.AddressCompositeDTO;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.dto.CompanyDTO;
import com.recycle.recycle.dto.PersonDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompanyMapperTest {

    private CompanyMapper companyMapper = Mappers.getMapper(CompanyMapper.class);
    private AddressComposite addressComposite = new AddressComposite("rue des johns", "3", "j4k4j4");
    private Address address = new Address(addressComposite, "Saint Jean Sur Richelieu", "Canada");
    List<String> companyIds = Arrays.asList("123");
    List<String> establishmentIds = Arrays.asList("321");

    private PersonDTO personDTO = new PersonDTO(
            "John",
            "438-111-1111",
            "john@john.ca",
            new AddressDTO(new AddressCompositeDTO("John", "3",
                    "j4k4j4"),
                    "Saint Jean Sur Richelieu",
                    "Canada"), companyIds, establishmentIds
    );

    @Test
    void convertToDTO() {
        Company company = new Company();
        company.setName("exemple");
        CompanyDTO companyDTO = companyMapper.convertToDTO(company);
        assertEquals(company.getName(), companyDTO.name());
    }

    @Test
    void convertToCompany() {
        AddressCompositeDTO addressKey = new AddressCompositeDTO("Rue Saint Jean", "2", "J2w2T5");
        AddressDTO addressDTO = new AddressDTO(addressKey, "Saint Jean", "Canada");
        List<Person> employess = new ArrayList<>();
        Person person1 = new Person();
        person1.setName("gaspar");

        CompanyDTO companyDTO = new CompanyDTO("exmple", "111", "exemple@exmple.com", addressDTO, employess);
        Company company = companyMapper.convertToCompany(companyDTO);
        assertEquals(companyDTO.name(), company.getName());
    }
}