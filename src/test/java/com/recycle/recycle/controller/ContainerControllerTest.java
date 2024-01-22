package com.recycle.recycle.controller;

import com.recycle.recycle.domain.ContainerMaterial;
import com.recycle.recycle.domain.Material;
import com.recycle.recycle.dto.ContainerDTO;
import com.recycle.recycle.dto.ExceptionDTO;
import com.recycle.recycle.mapper.ContainerMapper;
import com.recycle.recycle.repository.ContainerRepository;
import com.recycle.recycle.service.ContainerService;
import com.recycle.recycle.service.EntityService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContainerControllerTest {
    @InjectMocks
    ContainerController containerController;
    @Mock
    ContainerService containerService;

    ContainerDTO containerDTO;
    List<ContainerMaterial> containerList;
    private String notFound = "Container Not Found!";
    private String alreadyExist = "Container Already Exist";
    private String deleted = "Container deleted successfully!";

    @BeforeEach
    void setUp() {
        containerDTO = new ContainerDTO(
                new Material("123", "Glass"),
                "500ml", "0,10"
        );
        containerList = Arrays.asList(
                new ContainerMaterial("container123",
                        new Material("123", "Glass"),
                        "500ml", "0,10")
        );
    }

    @DisplayName("Test to register Container and return CREATED")
    @Test
    void registerContainerReturnCreated() {
        when(containerService.registerContainer(any())).thenReturn(containerDTO);
        ResponseEntity<?> response = containerController.registerContainer(containerDTO);
        verify(containerService, times(1)).registerContainer(containerDTO);
        assertEquals(response.getBody(), containerDTO);
    }

    @DisplayName("Test to register Container and return Already Exist")
    @Test
    void registerContainerReturnAlreadyExist() {
        when(containerService.registerContainer(any())).thenThrow(new DataIntegrityViolationException(alreadyExist));
        ResponseEntity<?> response = containerController.registerContainer(containerDTO);
        ExceptionDTO exceptionDTO = new ExceptionDTO(alreadyExist, response.getStatusCode().value());
        verify(containerService, times(1)).registerContainer(containerDTO);
        assertEquals(exceptionDTO, response.getBody());
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @DisplayName("Test to get all Containers and return OK")
    @Test
    void getAllContainerReturnOK() {
        when(containerService.getAllContainer()).thenReturn(containerList);
        ResponseEntity<?> response = containerController.getAllContainer();
        verify(containerService, times(1)).getAllContainer();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), containerList);
    }

    @DisplayName("Test to get Container By Id and return OK")
    @Test
    void getContainerByIdReturnOK() {
        when(containerService.getContainerById(containerList.get(0).getContainerId())).thenReturn(Optional.ofNullable(containerList.get(0)));
        ResponseEntity<?> response = containerController.getContainerById(containerList.get(0).getContainerId());
        verify(containerService, times(1)).getContainerById(containerList.get(0).getContainerId());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), Optional.ofNullable(containerList.get(0)));
    }

    @DisplayName("Test to get Container By ID and return Not Found Exception")
    @Test
    void getContainerByIdReturnNotFound() {
        when(containerService.getContainerById(containerList.get(0).getContainerId())).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> containerController.getContainerById(containerList.get(0).getContainerId()));
        verify(containerService, times(1)).getContainerById(containerList.get(0).getContainerId());
        assertEquals(exception.getMessage(), notFound);

    }

    @DisplayName("Test to Update Container and return ok")
    @Test
    void updateContainerReturnOK() {
        when(containerService.updateContainer(containerList.get(0).getContainerId(), containerDTO)).thenReturn(containerDTO);
        ResponseEntity<?> response = containerController.updateContainer(containerList.get(0).getContainerId(), containerDTO);
        verify(containerService, times(1)).updateContainer(containerList.get(0).getContainerId(), containerDTO);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), containerDTO);
    }

    @DisplayName("Test to Update Container and return not Found Exception")
    @Test
    void updateContainerThrowNotFound() {
        when(containerService.updateContainer(containerList.get(0).getContainerId(), containerDTO)).thenThrow(new DataIntegrityViolationException(notFound));
        ResponseEntity<?> response = containerController.updateContainer(containerList.get(0).getContainerId(), containerDTO);
        ExceptionDTO exceptionDTO = new ExceptionDTO(notFound, response.getStatusCodeValue());
        verify(containerService, times(1)).updateContainer(containerList.get(0).getContainerId(), containerDTO);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(notFound, exceptionDTO.message());

    }

    @DisplayName("Test to deleteContainer and return OK")
    @Test
    void deleteContainerWithSucess() {
        when(containerService.deleteContainer(containerList.get(0).getContainerId())).thenReturn(true);
        ResponseEntity<?> response = containerController.deleteContainer(containerList.get(0).getContainerId());
        verify(containerService, times(1)).deleteContainer(containerList.get(0).getContainerId());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(deleted, response.getBody());
    }

    @DisplayName("Test to deleteContainer and return OK")
    @Test
    void deleteContainerThrowNorFound() {
        when(containerService.deleteContainer(containerList.get(0).getContainerId())).thenReturn(false);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> containerController.deleteContainer(containerList.get(0).getContainerId()));
        verify(containerService, times(1)).deleteContainer(containerList.get(0).getContainerId());
        assertEquals(notFound, exception.getMessage());
    }
}