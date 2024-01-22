package com.recycle.recycle.mapper;

import com.recycle.recycle.controller.ContainerController;
import com.recycle.recycle.domain.ContainerMaterial;
import com.recycle.recycle.domain.Material;
import com.recycle.recycle.dto.ContainerDTO;
import com.recycle.recycle.service.ContainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContainerMapperTest {
    private ContainerMapper containerMapper = Mappers.getMapper(ContainerMapper.class);
    private ContainerDTO containerDTO;
    private List<ContainerMaterial> containerList;

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

    @DisplayName("Test to convert Container in containerDTO")
    @Test
    void convertToDTO() {
        ContainerDTO containerToDTO = containerMapper.convertToDTO(containerList.get(0));
        assertEquals(containerList.get(0).getMaterial(), containerToDTO.material());
    }

    @DisplayName("Test to convert containerDTO in Container")
    @Test
    void convertToContainer() {
        ContainerMaterial containerMaterial = containerMapper.convertToContainer(containerDTO);
        assertEquals(containerDTO.material(), containerMaterial.getMaterial());
    }
}