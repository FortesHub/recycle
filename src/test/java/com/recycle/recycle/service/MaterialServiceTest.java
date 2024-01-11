package com.recycle.recycle.service;

import com.recycle.recycle.domain.Material;
import com.recycle.recycle.dto.MaterialDTO;
import com.recycle.recycle.mapper.MaterialMapper;
import com.recycle.recycle.repository.MaterialRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaterialServiceTest {
    @InjectMocks
    private MaterialService materialService;
    @Mock
    private MaterialRepository materialRepository;
    @Spy
    private MaterialMapper materialMapper = Mappers.getMapper(MaterialMapper.class);
    private MaterialDTO materialDTO;
    private List<Material> materiallist;

    @BeforeEach
    public void setUp() {
        materialDTO = new MaterialDTO("plastic");
        materiallist = Arrays.asList(
                new Material("1", "plastic"),
                new Material("2", "glass")
        );
    }

    @DisplayName("Test for register Material and return Material")
    @Test
    void givenMaterialwhenGetMaterial() {
        when(materialRepository.save(any())).thenReturn(materiallist.get(0));
        MaterialDTO resultDTO = materialService.registerMaterial(materialDTO);
        verify(materialRepository, times(1)).save(any(Material.class));
        assertEquals(materialDTO, resultDTO);
    }

    @DisplayName("Test for get all Material and return list of Material")
    @Test
    void givenMaterialListWhenGetAllMaterial() {
        when(materialRepository.findAll()).thenReturn(materiallist);
        List<Material> resultMaterial = materialService.getAllMaterial();
        verify(materialRepository, times(1)).findAll();
        assertEquals(materiallist.size(), resultMaterial.size());
    }

    @DisplayName("Test for get Material By id and return Material")
    @Test
    void givenMaterialWhenGetMaterialById() {
        when(materialRepository.findById(materiallist.get(1).getMaterialId())).thenReturn(Optional.ofNullable(materiallist.get(1)));
        Optional<Material> resultMaterial = materialService.getMaterialById(materiallist.get(1).getMaterialId());
        verify(materialRepository, times(1)).findById(materiallist.get(1).getMaterialId());
        assertEquals(Optional.of(materiallist.get(1)), resultMaterial);
    }

    @DisplayName("Test for update Material and return Material")
    @Test
    void givenMaterialUpdatedWhenGetMaterial() {
        MaterialDTO updateMaterial = new MaterialDTO("updatePlastic");
        Material DTOtoMaterial = new Material("1", "updatePlastic");

        when(materialRepository.save(any())).thenReturn(DTOtoMaterial);
        MaterialDTO resultMaterialDTO = materialService.updateMaterial(DTOtoMaterial.getMaterialId(), updateMaterial);
        verify(materialRepository, times(1)).save(any());
        assertEquals("updatePlastic", resultMaterialDTO.type());
    }

    @DisplayName("Test for get Material By id and return MaterialDeleted")
    @Test
    void givenMessageMaterialWasDeletedWhenGetMaterialById() {
        when(materialRepository.deleteByTypeIgnoreCase(materiallist.get(0).getType())).thenReturn(materiallist);
        boolean resultMaterial = materialService.deleteMaterial(materiallist.get(0).getType());
        verify(materialRepository, times(1)).deleteByTypeIgnoreCase(materiallist.get(0).getType());
        assertEquals(true, resultMaterial);
    }
}

