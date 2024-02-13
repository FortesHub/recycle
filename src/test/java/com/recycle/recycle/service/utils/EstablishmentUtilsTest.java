package com.recycle.recycle.service.utils;

import com.recycle.recycle.domain.*;
import com.recycle.recycle.dto.AddressCompositeDTO;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.dto.EstablishmentDTO;
import com.recycle.recycle.repository.EstablishmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstablishmentUtilsTest {
    @InjectMocks
    EstablishmentUtils establishmentUtils;
    @Mock
    EstablishmentRepository establishmentRepository;
    private List<Establishment> establishments;
    private List<String> establishmentIds;
    private List<Person> employees;
    private Address address;
    private AddressComposite addressComposite;
    private List<Machine> machines;
    private List<Material> containers;
    private Status status;

    @BeforeEach
    void setUp() {
        addressComposite = new AddressComposite("rue des johns", "3", "j4k4j4");
        address = new Address(addressComposite, "Saint Jean Sur Richelieu", "Canada");
        employees = Arrays.asList(new Person("personId123", "John Doe", "123456789",
                "john.doe@example.com"));
        status = Status.WORK;
        containers = Arrays.asList(new Material("materialId123", "paper"));
        machines = Arrays.asList(new Machine("machineId123", status, false, containers));
        establishments = Arrays.asList(
                new Establishment("123", "establishment", "222", "uniprix@uniprix", address, employees, machines)
        );
        establishmentIds = Arrays.asList("123");
    }

    @DisplayName("Test for get Establishments ids and return List Establishment")
    @Test
    void givenListEstablishmentWhenGetEstaBlishmentsIds() {
        when(establishmentRepository.findById(establishments.get(0).getEstablishmentId())).thenReturn(Optional.ofNullable(establishments.get(0)));
        List<Establishment> result = establishmentUtils.getEstablishments(establishmentIds);
        assertEquals(establishments.size(), result.size());
        verify(establishmentRepository, times(1)).findById(establishments.get(0).getEstablishmentId());
    }
}
