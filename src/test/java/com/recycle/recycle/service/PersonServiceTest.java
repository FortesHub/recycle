package com.recycle.recycle.service;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.domain.AddressComposite;
import com.recycle.recycle.domain.Company;
import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.AddressCompositeDTO;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.dto.PersonDTO;
import com.recycle.recycle.mapper.PersonMapper;
import com.recycle.recycle.repository.AddressRepository;
import com.recycle.recycle.repository.CompanyRepository;
import com.recycle.recycle.repository.PersonRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @InjectMocks
    private PersonService personService;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private CompanyRepository companyRepository;
    @Spy
    private PersonMapper personMapper = Mappers.getMapper(PersonMapper.class);

    private PersonDTO personDTO;
    private List<Person> personList;
    private List<Address> addressList;
    private List<Company> companies;

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

    @DisplayName("Test for register person and return Person")
    @Test
    void givenPersonObject_whenRegisterPerson() {
        when(addressRepository.findByAddressComposite(any())).thenReturn(addressList.get(0));
        when(personRepository.save(any())).thenReturn(personList.get(0));
        when(companyRepository.findById(anyString())).thenReturn(Optional.of(companies.get(0)));
        PersonDTO result = personService.registerPerson(personDTO);
        verify(personRepository, times(1)).save(any());
        verify(addressRepository, times(1)).findByAddressComposite(addressList.get(0).getAddressComposite());
        verify(companyRepository, times(1)).findById(companies.get(0).getCompanyId());
        assertNotNull(result);
        assertEquals(personDTO.name(), result.name());
    }


    @DisplayName("Test for get all person and return list of person")
    @Test
    void givenPersonList_whenGetAllPerson() {
        when(personRepository.findAll()).thenReturn(personList);
        List<Person> resultList = personService.getAllPerson();
        assertEquals(personList.size(), resultList.size());
        verify(personRepository, times(1)).findAll();
    }

    @DisplayName("Test for find person By Id and return Person")
    @Test
    void givenOptionalPerson_whenGetPersonById() {
        String personId = "321";
        when(personRepository.findById(personId)).thenReturn(Optional.of(personList.get(0)));
        Optional<Person> resultPerson = personService.getPersonById(personId);
        verify(personRepository, times(1)).findById(personId);
        assertEquals(Optional.of(personList.get(0)), resultPerson);
    }

    @DisplayName("Test for update Person with existing Id")
    @Test
    void givenIdPersonAndUpdatePerson() {
        String id = "321";
        Company company = new Company("companyId1", "Company1", "123456789",
                "company@example.com", addressList.get(0), List.of());
        personDTO = new PersonDTO("John", "123456789", "john.doe@example.com",
                new AddressDTO(new AddressCompositeDTO("rue des Pines", "3", "j4k4j4"),
                        "Saint Jean Sur Richelieu", "Canada"), List.of("companyId1"));
        Person personDTOtoPerson =  new Person("321", "updatedJohn", "438-111-1111", "updatedjohn@gmail.com", new Address(new AddressComposite("rue des johns",
                "3", "j4k4j4"), "Saint Jean Sur Richelieu", "Canada"), List.of(new Company("companyId1", "Company1", "123456789",
                "company@example.com", addressList.get(0), List.of())));

        when(personRepository.save(any(Person.class))).thenReturn(personDTOtoPerson);
        when(companyRepository.findById(anyString())).thenReturn(Optional.of(company));
        PersonDTO resultDTO = personService.updatePerson(personDTOtoPerson.getPersonId(), personDTO);
        verify(personRepository, times(1)).save(any(Person.class));
        assertEquals("updatedJohn", resultDTO.name());
        assertEquals("438-111-1111", resultDTO.telephone());
        assertEquals("updatedjohn@gmail.com", resultDTO.email());
        assertEquals("rue des johns", resultDTO.address().addressComposite().street());
        assertEquals("3", resultDTO.address().addressComposite().complement());
        assertEquals("j4k4j4", resultDTO.address().addressComposite().postalCode());
        assertEquals("Saint Jean Sur Richelieu", resultDTO.address().city());
        assertEquals("Canada", resultDTO.address().pays());
        assertEquals("companyId1", resultDTO.companyIds().get(0));

    }

    @DisplayName("Test for find person By Id and delete Person")
    @Test
    void givenIdPersonAndDeletePerson() {
        String personId = "321";
        when(personRepository.findById(personId)).thenReturn(Optional.of(personList.get(0)));
        boolean resultPerson = personService.deletePerson(personId);
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(1)).delete(personList.get(0));
        assertTrue(resultPerson);
    }
}