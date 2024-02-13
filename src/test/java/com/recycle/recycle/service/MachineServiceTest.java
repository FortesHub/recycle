package com.recycle.recycle.service;

import com.recycle.recycle.domain.Machine;
import com.recycle.recycle.domain.Material;
import com.recycle.recycle.domain.Status;
import com.recycle.recycle.dto.MachineDTO;
import com.recycle.recycle.mapper.MachineMapper;
import com.recycle.recycle.repository.MachineRepository;
import com.recycle.recycle.service.utils.MaterialUtils;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MachineServiceTest {
    @InjectMocks
    MachineService machineService;
    @Mock
    MachineRepository machineRepository;
    @Mock
    MaterialUtils materialUtils;
    @Spy
    MachineMapper containerMapper = Mappers.getMapper(MachineMapper.class);
    private MachineDTO machineDTO;
    private List<Machine> machines;
    private List<Material> containers;
    private List<String> containersString;
    private Status status;

    @BeforeEach
    void setUp() {
        status = Status.DEFECT;
        containers = Arrays.asList(new Material("materialId123", "paper"));
        containersString = Arrays.asList("materialId123");
        machineDTO = new MachineDTO(status, false, containersString);
        machines = Arrays.asList(new Machine("machineId123", status, false, containers));
    }

    @DisplayName("Test to register Machine and return Machine")
    @Test
    void givenMachineWhenGetMachine() {
        when(materialUtils.getContainers(any())).thenReturn(containers);
        when(machineRepository.save(any())).thenReturn(machines.get(0));
        MachineDTO registeredMachine = machineService.registerMachine(machineDTO);
        verify(machineRepository, times(1)).save(any());
        assertEquals(registeredMachine, machineDTO);
    }

    @DisplayName("Test to get all list Machine")
    @Test
    void givenListMachineWhenGetAllMachine() {
        when(machineRepository.findAll()).thenReturn(machines);
        List<Machine> resultList = machineService.getAllMachine();
        verify(machineRepository, times(1)).findAll();
        assertEquals(resultList, machines);
    }

    @DisplayName("Test to get Machine By Id and return Machine")
    @Test
    void givenMachineWhenGetMachineById() {
        when(machineRepository.findById(any())).thenReturn(Optional.ofNullable(machines.get(0)));
        Optional<Machine> resultMachine = machineService.getMachineById(machines.get(0).getMachineId());
        verify(machineRepository, times(1)).findById(machines.get(0).getMachineId());
        assertEquals(resultMachine, Optional.of(machines.get(0)));
    }

    @DisplayName("Test to update Machine and return Machine updated")
    @Test
    void givenMachineUpdatedWhenUpdateMachine() {
        String machineId = "123";
        when(materialUtils.getContainers(any())).thenReturn(containers);
        when(machineRepository.save(any())).thenReturn(machines.get(0));
        MachineDTO updatedMachine = machineService.updateMachine(machineId, machineDTO);
        assertEquals(machineDTO, updatedMachine);
    }

    @DisplayName("Test to delete Machine and show phrase Machine Deleted")
    @Test
    void givenMachineDeletedWhenGetMachineById() {
        when(machineRepository.findById(any())).thenReturn(Optional.of(machines.get(0)));
        boolean deletedMachine = machineService.deleteMachine(machines.get(0).getMachineId());
        verify(machineRepository, times(1)).findById(machines.get(0).getMachineId());
        assertTrue(true);
    }
}