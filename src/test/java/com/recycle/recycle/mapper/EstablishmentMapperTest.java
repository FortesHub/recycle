package com.recycle.recycle.mapper;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.domain.AddressComposite;
import com.recycle.recycle.domain.Establishment;
import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.AddressCompositeDTO;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.dto.CompanyDTO;
import com.recycle.recycle.dto.EstablishmentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EstablishmentMapperTest {
    private EstablishmentMapper establishmentMapper = Mappers.getMapper(EstablishmentMapper.class);
    private EstablishmentDTO establishmentDTO;
    private Establishment establishment;
    private AddressComposite addressComposite;
    private AddressCompositeDTO addressCompositeDTO;
    private Address address;
    private AddressDTO addressDTO;
    private List<Person> employees;

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
        establishmentDTO = new EstablishmentDTO("establishmentDTO", "222", "uniprix@uniprix", addressDTO, employees);
        establishment = new Establishment("123", "establishment", "222", "uniprix@uniprix", address, employees);
    }

    @Test
    void convertToEstablishment() {
        establishment = establishmentMapper.convertToEstablishment(establishmentDTO);
        assertEquals(establishmentDTO.name(), establishment.getName());

    }

    @Test
    void establishmentToDTO() {
        establishmentDTO = establishmentMapper.establishmentToDTO(establishment);
        assertEquals(establishment.getName(), establishmentDTO.name());
    }
}