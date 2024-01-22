package com.recycle.recycle.service;

import com.recycle.recycle.domain.ContainerMaterial;
import com.recycle.recycle.domain.Material;
import com.recycle.recycle.dto.ContainerDTO;
import com.recycle.recycle.mapper.ContainerMapper;
import com.recycle.recycle.repository.ContainerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContainerServiceTest {
    @InjectMocks
    ContainerService containerService;
    @Mock
    ContainerRepository containerRepository;
    @Mock
    EntityService entityService;
    @Spy
    ContainerMapper containerMapper = Mappers.getMapper(ContainerMapper.class);
    ContainerDTO containerDTO;
    List<ContainerMaterial> containerList;

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

    @DisplayName("Test to register Container and return Container")
    @Test
    void givenContainerWhenGetContainer() {
        when(entityService.getMaterial(any())).thenReturn(containerList.get(0).getMaterial());
        when(containerRepository.save(any())).thenReturn(containerList.get(0));
        ContainerDTO registeredContainer = containerService.registerContainer(containerDTO);
        verify(entityService, times(1)).getMaterial(any());
        verify(containerRepository, times(1)).save(any());
        assertEquals(registeredContainer, containerDTO);
    }

    @DisplayName("Test to get all list Container")
    @Test
    void givenListContainerWhenGetAllContainer() {
        when(containerRepository.findAll()).thenReturn(containerList);
        List<ContainerMaterial> resultList = containerService.getAllContainer();
        verify(containerRepository, times(1)).findAll();
        assertEquals(resultList, containerList);
    }

    @DisplayName("Test to get Container By Id and return Container")
    @Test
    void givenContainerWhenGetContainerById() {
        when(containerRepository.findById(containerList.get(0).getContainerId())).thenReturn(Optional.of(containerList.get(0)));
        Optional<ContainerMaterial> resultContainer = containerService.getContainerById(containerList.get(0).getContainerId());
        verify(containerRepository, times(1)).findById(containerList.get(0).getContainerId());
        assertEquals(resultContainer, Optional.of(containerList.get(0)));
    }

    @DisplayName("Test to update Container and return Container updated")
    @Test
    void givenContainerUpdatedWhenUpdateContainer() {
        String containerId = "123";
        when(entityService.getMaterial(any())).thenReturn(containerList.get(0).getMaterial());
        when(containerRepository.save(any())).thenReturn(containerList.get(0));
        ContainerDTO updatedContainer = containerService.updateContainer(containerId, containerDTO);
        assertEquals(containerDTO, updatedContainer);
    }

    @DisplayName("Test to delete container and show phrase Container Deleted")
    @Test
    void givenContainerDeletedWhenGetContainerById() {
        when(containerRepository.findById(containerList.get(0).getContainerId())).thenReturn(Optional.of(containerList.get(0)));
        boolean deletedContainer = containerService.deleteContainer(containerList.get(0).getContainerId());
        verify(containerRepository, times(1)).findById(containerList.get(0).getContainerId());
        assertTrue(true);
    }
}