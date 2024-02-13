package com.recycle.recycle.mapper.utils;

import com.recycle.recycle.domain.*;
import com.recycle.recycle.dto.AddressCompositeDTO;
import com.recycle.recycle.dto.AddressDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MappersUtilsTest {
    private MappersUtils mappersUtils = Mappers.getMapper(MappersUtils.class);
    private List<Person> employees;
    private List<String> employeesString;
    private AddressComposite addressComposite;
    private Address address;
    private AddressCompositeDTO addressCompositeDTO;
    private AddressDTO addressDTO;
    private List<Establishment> establishments;
    private List<String> establishmentsString;
    private List<Machine> machines;
    private List<String> machinesString;
    private List<Material> containers;
    private List<String> containersString;
    private Status status;

    @BeforeEach
    public void setUp() {
        addressComposite = new AddressComposite("rue des johns", "3", "j4k4j4");
        address = new Address(addressComposite, "Saint Jean Sur Richelieu", "Canada");
        addressCompositeDTO = new AddressCompositeDTO("rue des johns", "3", "j4k4j4");
        addressDTO = new AddressDTO(addressCompositeDTO, "Saint Jean Sur Richelieu", "Canada");
        employees = Arrays.asList(new Person("personId123", "John Doe", "123456789",
                "john.doe@example.com"));
        employeesString = Arrays.asList("personId123");
        status = Status.WORK;
        containers = Arrays.asList(new Material("materialId123", "paper"));
        containersString = Arrays.asList("materialId123");
        machines = Arrays.asList(new Machine("machineId123", status, false, containers));
        machinesString = Arrays.asList("machineId123");
        establishments = Arrays.asList(new Establishment("establishmentId123", "Walamart", "4444", "walmart@gmail.com", address, employees, machines));
        establishmentsString = Arrays.asList("establishmentId123");
    }

    @DisplayName("Teste to map List<String> establishments")
    @Test
    void mapEstablishments() {
        List<String> mappedEstablishments = mappersUtils.mapEstablishments(establishments);
        assertEquals(establishmentsString, mappedEstablishments);
    }

    @DisplayName("Teste to map List<String> employees")
    @Test
    void mapEmployees() {
        List<String> mappedEmployees = mappersUtils.mapEmployees(employees);
        assertEquals(employeesString, mappedEmployees);
    }

    @DisplayName("Teste to map List<String> machines")
    @Test
    void mapMachines() {
        List<String> mappedMachines = mappersUtils.mapMachines(machines);
        assertEquals(machinesString, mappedMachines);
    }

    @DisplayName("Teste to map List<String> containers")
    @Test
    void mapContainers() {
        List<String> mappedContainers = mappersUtils.mapContainers(containers);
        assertEquals(containersString, mappedContainers);
    }


}
