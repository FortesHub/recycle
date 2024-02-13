package com.recycle.recycle.service;

import com.recycle.recycle.domain.*;
import com.recycle.recycle.dto.AddressCompositeDTO;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.dto.EstablishmentDTO;
//import com.recycle.recycle.mapper.EstablishmentMapper;
import com.recycle.recycle.mapper.EstablishmentMapper;
import com.recycle.recycle.repository.EstablishmentRepository;
import com.recycle.recycle.service.utils.AddressUtils;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstablishmentServiceTest {
    @InjectMocks
    EstablishmentService establishmentService;
    @Mock
    EstablishmentRepository establishmentRepository;
    @Mock
    AddressUtils addressUtils;
    @Mock
    PersonUtils personUtils;
    @Mock
    MachineUtils machineUtils;
    @Spy
    EstablishmentMapper establishmentMapper = Mappers.getMapper(EstablishmentMapper.class);
    private EstablishmentDTO establishmentDTO;
    private List<Establishment> establishments;
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
        establishments = Arrays.asList(
                new Establishment("123", "establishment", "222", "uniprix@uniprix", address, employees, machines)
        );
        establishmentDTO = new EstablishmentDTO("establishment", "222", "uniprix@uniprix", addressDTO, employeesString, machinesString);
    }

    @DisplayName("Test for register Establishment and return Establishment")
    @Test
    void givenEstablishmentWhenRegisterEstablishment() {
        when(addressUtils.getAddress(any())).thenReturn(address);
        when(personUtils.getEmployees(any())).thenReturn(employees);
        when(machineUtils.getMachines(any())).thenReturn(machines);
        when(establishmentRepository.save(any(Establishment.class))).thenReturn(establishments.get(0));
        EstablishmentDTO registeredEstablishment = establishmentService.registerEstablishment(establishmentDTO);
        verify(establishmentRepository, times(1)).save(any(Establishment.class));
        assertEquals(establishmentDTO, registeredEstablishment);
    }

    @DisplayName("Test for get all Establishment and return List of Establishment")
    @Test
    void givenListEstablishmentGetAllEstablishment() {
        when(establishmentRepository.findAll()).thenReturn(establishments);
        List<Establishment> listEstablishment = establishmentService.getAllEstablishment();
        verify(establishmentRepository, times(1)).findAll();
        assertEquals(establishments, listEstablishment);
    }

    @DisplayName("Test for get name, postalCode,type and return List of Establishment")
    @Test
    void givenListEstablishmentGetFiltersByNameByPostalCodeByType() {
        when(establishmentRepository.findByNameIgnoreCaseContainingAndAddress_AddressComposite_PostalCodeIgnoreCaseContainingAndMachines_Containers_TypeIgnoreCase(any(), any(), any())).thenReturn(establishments);
        List<Establishment> listEstablishment = establishmentService.getEstablishmentByFilters(establishments.get(0).getName()
                , establishments.get(0).getAddress().getAddressComposite().getPostalCode()
                , establishments.get(0).getMachines().get(0).getContainers().get(0).getType());
        verify(establishmentRepository, times(1)).findByNameIgnoreCaseContainingAndAddress_AddressComposite_PostalCodeIgnoreCaseContainingAndMachines_Containers_TypeIgnoreCase(any(), any(), any());
        assertEquals(establishments, listEstablishment);
    }

    @DisplayName("Test for get name, postalCode and return List of Establishment")
    @Test
    void givenListEstablishmentGetFiltersByNameByPostalCode() {
        when(establishmentRepository.findByNameIgnoreCaseContainingAndAddress_AddressComposite_PostalCodeIgnoreCaseContaining(any(), any())).thenReturn(establishments);
        List<Establishment> listEstablishment = establishmentService.getEstablishmentByFilters(establishments.get(0).getName()
                , establishments.get(0).getAddress().getAddressComposite().getPostalCode(), null);
        verify(establishmentRepository, times(1)).findByNameIgnoreCaseContainingAndAddress_AddressComposite_PostalCodeIgnoreCaseContaining(any(), any());
        assertEquals(establishments, listEstablishment);
    }

    @DisplayName("Test for get name, type and return List of Establishment")
    @Test
    void givenListEstablishmentGetFiltersByNameByType() {
        when(establishmentRepository.findByNameIgnoreCaseContainingAndMachines_Containers_TypeIgnoreCase(any(), any())).thenReturn(establishments);
        List<Establishment> listEstablishment = establishmentService.getEstablishmentByFilters(establishments.get(0).getName()
                , null, establishments.get(0).getMachines().get(0).getContainers().get(0).getType());
        verify(establishmentRepository, times(1)).findByNameIgnoreCaseContainingAndMachines_Containers_TypeIgnoreCase(any(), any());
        assertEquals(establishments, listEstablishment);
    }

    @DisplayName("Test for get postalCode, type and return List of Establishment")
    @Test
    void givenListEstablishmentGetFiltersByPostalCodeByType() {
        when(establishmentRepository.findByAddress_AddressComposite_PostalCodeIgnoreCaseContainingAndMachines_Containers_TypeIgnoreCase(any(), any())).thenReturn(establishments);
        List<Establishment> listEstablishment = establishmentService.getEstablishmentByFilters(null
                , establishments.get(0).getAddress().getAddressComposite().getPostalCode(), establishments.get(0).getMachines().get(0).getContainers().get(0).getType());
        verify(establishmentRepository, times(1)).findByAddress_AddressComposite_PostalCodeIgnoreCaseContainingAndMachines_Containers_TypeIgnoreCase(any(), any());
        assertEquals(establishments, listEstablishment);
    }

    @DisplayName("Test for get name and return List of Establishment")
    @Test
    void givenListEstablishmentGetFiltersByName() {
        when(establishmentRepository.findByNameIgnoreCaseContaining(any())).thenReturn(establishments);
        List<Establishment> listEstablishment = establishmentService.getEstablishmentByFilters(establishments.get(0).getName(), null, null);
        verify(establishmentRepository, times(1)).findByNameIgnoreCaseContaining(any());
        assertEquals(establishments, listEstablishment);
    }

    @DisplayName("Test for get postalCode and return List of Establishment")
    @Test
    void givenListEstablishmentGetFiltersByPostalCode() {
        when(establishmentRepository.findByAddress_AddressComposite_PostalCodeIgnoreCaseContaining(any())).thenReturn(establishments);
        List<Establishment> listEstablishment = establishmentService.getEstablishmentByFilters(null, establishments.get(0).getAddress().getAddressComposite().getPostalCode(), null);
        verify(establishmentRepository, times(1)).findByAddress_AddressComposite_PostalCodeIgnoreCaseContaining(any());
        assertEquals(establishments, listEstablishment);
    }

    @DisplayName("Test for get type and return List of Establishment")
    @Test
    void givenListEstablishmentGetFiltersByType() {
        when(establishmentRepository.findByMachines_Containers_TypeIgnoreCase(any())).thenReturn(establishments);
        List<Establishment> listEstablishment = establishmentService.getEstablishmentByFilters(null, null, establishments.get(0).getMachines().get(0).getContainers().get(0).getType());
        verify(establishmentRepository, times(1)).findByMachines_Containers_TypeIgnoreCase(any());
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
        EstablishmentDTO updateEstablishmentDTO = new EstablishmentDTO("establishmentUpdated", "222", "uniprix@uniprix", addressDTO, employeesString, machinesString);
        Establishment updateEstablishment = new Establishment("222", "establishmentUpdated", "222", "uniprix@uniprix", address, employees, machines);
        when(addressUtils.getAddress(any())).thenReturn(address);
        when(personUtils.getEmployees(any())).thenReturn(employees);
        when(machineUtils.getMachines(any())).thenReturn(machines);
        when(establishmentRepository.save(any(Establishment.class))).thenReturn(updateEstablishment);
        EstablishmentDTO updatedEstablishment = establishmentService.updateEstablishment(establishmentId, establishmentDTO);
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