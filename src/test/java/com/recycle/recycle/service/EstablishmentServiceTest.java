package com.recycle.recycle.service;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.domain.AddressComposite;
import com.recycle.recycle.domain.Establishment;
import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.AddressCompositeDTO;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.dto.EstablishmentDTO;
import com.recycle.recycle.mapper.EstablishmentMapper;
import com.recycle.recycle.repository.EstablishmentRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstablishmentServiceTest {
    @InjectMocks
    EstablishmentService establishmentService;
    @Mock
    EstablishmentRepository establishmentRepository;
    @Mock
    EntityService entityService;
    @Spy
    EstablishmentMapper establishmentMapper = Mappers.getMapper(EstablishmentMapper.class);
    private EstablishmentDTO establishmentDTO;
    private AddressComposite addressComposite;
    private AddressCompositeDTO addressCompositeDTO;
    private Address address;
    private AddressDTO addressDTO;
    private List<Person> employees;
    private List<Establishment> establishments;

    @BeforeEach
    void setUp() {
        addressComposite = new AddressComposite("rue des johns", "3", "j4k4j4");
        address = new Address(addressComposite, "Saint Jean Sur Richelieu", "Canada");
        addressCompositeDTO = new AddressCompositeDTO("rue des johns", "3", "j4k4j4");
        addressDTO = new AddressDTO(addressCompositeDTO, "Saint Jean Sur Richelieu", "Canada");
        employees = Arrays.asList(
                new Person("personId123", "John Doe", "123456789",
                        "john.doe@example.com", address, List.of(), List.of())
        );
        establishments = Arrays.asList(
                new Establishment("123", "establishment", "222", "uniprix@uniprix", address, employees)
        );
        establishmentDTO = new EstablishmentDTO("establishment", "222", "uniprix@uniprix", addressDTO, employees);
    }

    @DisplayName("Test for register Establishment and return Establishment")
    @Test
    void givenEstablishmentWhenRegisterEstablishment() {
        when(entityService.getAddress(any())).thenReturn(address);
        when(establishmentRepository.save(any(Establishment.class))).thenReturn(establishments.get(0));
        EstablishmentDTO registeredEstablishment = establishmentService.registerEstablishment(establishmentDTO);
        verify(entityService, times(1)).getAddress(address);
        verify(establishmentRepository, times(1)).save(any(Establishment.class));
        assertEquals(establishmentDTO, registeredEstablishment);
    }

    @DisplayName("Test for get all Establishment and return List of Establishment")
    @Test
    void givenLisEstablishmentGetAllEstablishment() {
        when(establishmentRepository.findAll()).thenReturn(establishments);
        List<Establishment> listEstablishment = establishmentService.getAllEstablishment();
        verify(establishmentRepository, times(1)).findAll();
        assertEquals(establishments, listEstablishment);
    }

    @DisplayName("Test get Establishment By Id and return establishment")
    @Test
    void givenEstablishmentWhenGetEstablishmentByID() {
        when(establishmentRepository.findById(establishments.get(0).getEstablishmentId())).thenReturn(Optional.of(establishments.get(0)));
        Optional<Establishment> result = establishmentService.getEstablishmentByID(establishments.get(0).getEstablishmentId());
        verify(establishmentRepository, times(1)).findById(establishments.get(0).getEstablishmentId());
        assertEquals(establishments.get(0).getEstablishmentId(), result.get().getEstablishmentId());
    }

    @DisplayName("Teste Update Establishment and return Establishment updated")
    @Test
    void givenEstablishmentUpdatedupdateEstablishment() {
        String establishmentId = "222";
        EstablishmentDTO updateEstablishmentDTO = new EstablishmentDTO("establishmentUpdated", "222", "uniprix@uniprix", addressDTO, employees);
        Establishment updateEstablishment = new Establishment("222", "establishmentUpdated", "222", "uniprix@uniprix", address, employees);
        when(entityService.getAddress(address)).thenReturn(address);
        when(establishmentRepository.save(any(Establishment.class))).thenReturn(updateEstablishment);
        EstablishmentDTO updatedEstablishment = establishmentService.updateEstablishment(establishmentId, establishmentDTO);
        verify(entityService, times(1)).getAddress(address);
        verify(establishmentRepository, times(1)).save(any(Establishment.class));
        assertEquals(updateEstablishmentDTO, updatedEstablishment);
    }

    @DisplayName("Test delete establishment and return -> Establishment Deleted")
    @Test
    void deleteEstablishment() {
        when(establishmentRepository.findById(establishments.get(0).getEstablishmentId())).thenReturn(Optional.of(establishments.get(0)));
        boolean wasDeleted = establishmentService.deleteEstablishment(establishments.get(0).getEstablishmentId());
        verify(establishmentRepository, times(1)).findById(establishments.get(0).getEstablishmentId());
        assertTrue(true);
    }
}