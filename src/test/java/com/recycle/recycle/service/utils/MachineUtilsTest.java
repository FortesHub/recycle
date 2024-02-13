package com.recycle.recycle.service.utils;

import com.recycle.recycle.domain.Machine;
import com.recycle.recycle.domain.Material;
import com.recycle.recycle.domain.Status;
import com.recycle.recycle.repository.MachineRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MachineUtilsTest {
    @InjectMocks
    MachineUtils machineUtils;
    @Mock
    MachineRepository machineRepository;
    private List<Machine> machines;
    private List<String> machineIds;
    private List<Material> containers;
    private List<String> containersString;
    private Status status;

    @BeforeEach
    void setUp() {
        status = Status.DEFECT;
        containers = Arrays.asList(new Material("materialId123", "paper"));
        containersString = Arrays.asList("materialId123");
        machines = Arrays.asList(new Machine("machineId123", status, false, containers));
        machineIds = Arrays.asList("machineId123");
    }

    @DisplayName("Test to get get MachineIds and return List Machine")
    @Test
    void givenListMachinesWhenGetMachineIds(){
        when(machineRepository.findById(any())).thenReturn(Optional.ofNullable(machines.get(0)));
        List<Machine> machinesList = machineUtils.getMachines(machineIds);
        assertEquals(machines.size(), machinesList.size());
        verify(machineRepository, times(1)).findById(machines.get(0).getMachineId());
    }
}
