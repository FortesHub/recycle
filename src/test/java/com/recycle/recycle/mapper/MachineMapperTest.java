package com.recycle.recycle.mapper;

import com.recycle.recycle.domain.Machine;
import com.recycle.recycle.domain.Material;
import com.recycle.recycle.domain.Status;
import com.recycle.recycle.dto.MachineDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MachineMapperTest {
    private MachineMapper machineMapper = Mappers.getMapper(MachineMapper.class);
    private MachineDTO machineDTO;
    private Machine machine;
    private List<Material> containers;
    private List<String> containersString;
    private Status status;

    @BeforeEach
    void setUp() {
        status = Status.WORK;
        containers = Arrays.asList(new Material("materialId123", "paper"));
        containersString = Arrays.asList("materialId123");
        machineDTO = new MachineDTO(status, false, containersString);
        machine = new Machine("machineId123", status, false, containers);
    }

    @DisplayName("Test to convert Machine in MachineDTO")
    @Test
    void convertToDTO() {
        MachineDTO machineToDTO = machineMapper.convertToDTO(machine);
        assertEquals(machine.getStatus(), machineToDTO.status());
    }

    @DisplayName("Test to convert MachineDTO in Machine")
    @Test
    void convertToMachine() {
        Machine containerMaterial = machineMapper.convertToMachine(machineDTO);
        assertEquals(machineDTO.status(), machine.getStatus());
    }
}