package com.recycle.recycle.service.utils;

import com.recycle.recycle.domain.Material;
import com.recycle.recycle.repository.MaterialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaterialUtilsTest {

    @InjectMocks
    MaterialUtils materialUtils;
    @Mock
    MaterialRepository materialRepository;
    private List<Material> containers;
    private List<String> types;

    @BeforeEach
    void setUp() {
        containers = Arrays.asList(new Material("materialId123", "paper"));
        types = Arrays.asList("paper");
    }

    @DisplayName("Test to get material Ids and return List Containers ")
    @Test
    void givenListContainersWhenGetMaterialIds() {
        when(materialRepository.findByTypeIgnoreCase(any())).thenReturn(containers.get(0));
        List<Material> materialList = materialUtils.getContainers(types);
        assertEquals(materialList.get(0).getType(), containers.get(0).getType());
        verify(materialRepository, times(1)).findByTypeIgnoreCase(containers.get(0).getType());
    }
}
