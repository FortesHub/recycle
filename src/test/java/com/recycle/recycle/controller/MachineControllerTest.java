package com.recycle.recycle.controller;

import com.recycle.recycle.domain.Machine;
import com.recycle.recycle.domain.Material;
import com.recycle.recycle.domain.Status;
import com.recycle.recycle.dto.ExceptionDTO;
import com.recycle.recycle.dto.MachineDTO;
import com.recycle.recycle.service.MachineService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MachineControllerTest {
    @InjectMocks
    MachineController machineController;
    @Mock
    MachineService machineService;
    private MachineDTO machineDTO;
    private List<Machine> machines;
    private List<Material> containers;
    private List<String> containersString;
    private Status status;
    private String notFound = "Machine Not Found!";
    private String alreadyExist = "Machine Already Exist";
    private String deleted = "Machine deleted successfully!";

    @BeforeEach
    void setUp() {
        status = Status.DEFECT;
        containers = Arrays.asList(new Material("materialId123", "paper"));
        containersString = Arrays.asList("materialId123");
        machineDTO = new MachineDTO(status, false, containersString);
        machines = Arrays.asList(new Machine("machineId123", status, false, containers));
    }

    @DisplayName("Test to register Container and return CREATED")
    @Test
    void registerContainerReturnCreated() {
        when(machineService.registerMachine(any())).thenReturn(machineDTO);
        ResponseEntity<?> response = machineController.registerMachine(machineDTO);
        verify(machineService, times(1)).registerMachine(machineDTO);
        assertEquals(response.getBody(), machineDTO);
    }

    @DisplayName("Test to register Container and return Already Exist")
    @Test
    void registerContainerReturnAlreadyExist() {
        when(machineService.registerMachine(any())).thenThrow(new DataIntegrityViolationException(alreadyExist));
        ResponseEntity<?> response = machineController.registerMachine(machineDTO);
        ExceptionDTO exceptionDTO = new ExceptionDTO(alreadyExist, response.getStatusCode().value());
        verify(machineService, times(1)).registerMachine(machineDTO);
        assertEquals(exceptionDTO, response.getBody());
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @DisplayName("Test to get all Containers and return OK")
    @Test
    void getAllContainerReturnOK() {
        when(machineService.getAllMachine()).thenReturn(machines);
        ResponseEntity<?> response = machineController.getAllMachine();
        verify(machineService, times(1)).getAllMachine();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), machines);
    }

    @DisplayName("Test to get Container By Id and return OK")
    @Test
    void getContainerByIdReturnOK() {
        when(machineService.getMachineById(machines.get(0).getMachineId())).thenReturn(Optional.ofNullable(machines.get(0)));
        ResponseEntity<?> response = machineController.getMachineById(machines.get(0).getMachineId());
        verify(machineService, times(1)).getMachineById(machines.get(0).getMachineId());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), machines.get(0));
    }

    @DisplayName("Test to get Container By ID and return Not Found Exception")
    @Test
    void getContainerByIdReturnNotFound() {
        when(machineService.getMachineById(machines.get(0).getMachineId())).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> machineController.getMachineById(machines.get(0).getMachineId()));
        verify(machineService, times(1)).getMachineById(machines.get(0).getMachineId());
        assertEquals(exception.getMessage(), notFound);

    }

    @DisplayName("Test to Update Container and return ok")
    @Test
    void updateContainerReturnOK() {
        when(machineService.updateMachine(machines.get(0).getMachineId(), machineDTO)).thenReturn(machineDTO);
        ResponseEntity<?> response = machineController.updateMachine(machines.get(0).getMachineId(), machineDTO);
        verify(machineService, times(1)).updateMachine(machines.get(0).getMachineId(), machineDTO);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), machineDTO);
    }

    @DisplayName("Test to Update Container and return not Found Exception")
    @Test
    void updateContainerThrowNotFound() {
        when(machineService.updateMachine(machines.get(0).getMachineId(), machineDTO)).thenThrow(new DataIntegrityViolationException(notFound));
        ResponseEntity<?> response = machineController.updateMachine(machines.get(0).getMachineId(), machineDTO);
        ExceptionDTO exceptionDTO = new ExceptionDTO(notFound, response.getStatusCodeValue());
        verify(machineService, times(1)).updateMachine(machines.get(0).getMachineId(), machineDTO);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(notFound, exceptionDTO.message());

    }

    @DisplayName("Test to deleteContainer and return OK")
    @Test
    void deleteContainerWithSucess() {
        when(machineService.deleteMachine(machines.get(0).getMachineId())).thenReturn(true);
        ResponseEntity<?> response = machineController.deleteMachine(machines.get(0).getMachineId());
        verify(machineService, times(1)).deleteMachine(machines.get(0).getMachineId());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(deleted, response.getBody());
    }

    @DisplayName("Test to deleteContainer and return OK")
    @Test
    void deleteContainerThrowNorFound() {
        when(machineService.deleteMachine(machines.get(0).getMachineId())).thenReturn(false);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> machineController.deleteMachine(machines.get(0).getMachineId()));
        verify(machineService, times(1)).deleteMachine(machines.get(0).getMachineId());
        assertEquals(notFound, exception.getMessage());
    }
}