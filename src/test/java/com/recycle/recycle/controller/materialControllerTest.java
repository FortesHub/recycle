package com.recycle.recycle.controller;

import com.recycle.recycle.domain.Material;
import com.recycle.recycle.dto.ExceptionDTO;
import com.recycle.recycle.dto.MaterialDTO;
import com.recycle.recycle.infra.GlobalControllerAdvice;
import com.recycle.recycle.service.MaterialService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class materialControllerTest {
    @InjectMocks
    private MaterialController materialController;
    @Mock
    private MaterialService materialService;
    @Spy
    private GlobalControllerAdvice globalControllerException = new GlobalControllerAdvice();

    private MaterialDTO materialDTO;
    private List<Material> materiallist;
    private String materialNotFound = "Material Not Found!";
    private String alreadyExist = "Material Already Exist";
    private String deleted = "Material deleted successfully!";

    @BeforeEach
    public void setUp() {
        materialDTO = new MaterialDTO("plastic");
        materiallist = Arrays.asList(
                new Material("1", "plastic"),
                new Material("2", "glass")
        );
    }

    @DisplayName("Test register material and return created Material")
    @Test
    void whenRegisterMaterialReturnCreated() {
        when(materialService.registerMaterial(any())).thenReturn(materialDTO);
        ResponseEntity<?> response = materialController.registerMaterial(materialDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(response.getBody(), materialDTO);
        verify(materialService, times(1)).registerMaterial(materialDTO);
    }

    @DisplayName("Test to registerMaterial and return AlreadyExist")
    @Test
    void whenRegisterMaterialThrowAlreadyExist() {
        when(materialService.registerMaterial(materialDTO)).thenThrow(new DataIntegrityViolationException(alreadyExist));
        ResponseEntity<?> response = materialController.registerMaterial(materialDTO);
        ExceptionDTO exception = (ExceptionDTO) response.getBody();
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(exception.message(), ((ExceptionDTO) response.getBody()).message());
        verify(materialService, times(1)).registerMaterial(materialDTO);

    }

    @DisplayName("Test to get all material and return ok ")
    @Test
    void whenGetAllMaterialReturnOk() {
        when(materialService.getAllMaterial()).thenReturn(materiallist);
        ResponseEntity<List<Material>> materials = materialController.getAllMaterial();
        assertEquals(HttpStatus.OK, materials.getStatusCode());
        assertEquals(materials.getBody(), materiallist);
        verify(materialService, times(1)).getAllMaterial();
    }

    @DisplayName("Test to get material by id and return ok")
    @Test
    void whenGetMaterialByIdReturnOk() {
        when(materialService.getMaterialById(materiallist.get(0).getMaterialId())).thenReturn(Optional.ofNullable(materiallist.get(0)));
        ResponseEntity<?> response = materialController.getMaterialId(materiallist.get(0).getMaterialId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Optional<Material> optionalMaterial = (Optional<Material>) response.getBody();
        assertTrue(optionalMaterial.isPresent());
        assertEquals(optionalMaterial.get(), materiallist.get(0));
        verify(materialService, times(1)).getMaterialById(materiallist.get(0).getMaterialId());
    }

    @DisplayName("Test to get Material and throw Material Not Found")
    @Test
    void whenGetMaterialByIdThrowException() {
        String materialId = "123";
        when(materialService.getMaterialById(materialId)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            materialController.getMaterialId(materialId);
        });
        assertEquals(exception.getMessage(), materialNotFound);
        verify(materialService, times(1)).getMaterialById(materialId);
    }

    @DisplayName("Test to updateMaterial and return ok")
    @Test
    void whenUpdateMaterialReturnOk() {
        String materialId = "123";
        MaterialDTO updatedMaterialDTO = new MaterialDTO("updatedPlastic");
        when(materialService.updateMaterial(materialId, updatedMaterialDTO)).thenReturn(updatedMaterialDTO);
        ResponseEntity<?> response = materialController.updateMaterial(materialId, updatedMaterialDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(materialService, times(1)).updateMaterial(materialId, updatedMaterialDTO);
    }

    @DisplayName("Test to Update Material and return Material Id Not Found")
    @Test
    void whenUpdateMaterialThrowException() {
        String materialId = "123";
        MaterialDTO updatedMaterialDTO = new MaterialDTO("updatedPlastic");
        when(materialService.updateMaterial(materialId, updatedMaterialDTO)).thenThrow(new DataIntegrityViolationException(materialNotFound));
        ResponseEntity<?> response = materialController.updateMaterial(materialId, updatedMaterialDTO);
        ExceptionDTO exception = (ExceptionDTO) response.getBody();
        assertEquals(exception.message(), materialNotFound);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(materialService, times(1)).updateMaterial(materialId, updatedMaterialDTO);
    }

    @DisplayName("Test to delete Material and return ok")
    @Test
    void whenDeleteMaterialReturnOK() {
        when(materialService.deleteMaterial(materiallist.get(0).getType())).thenReturn(true);
        ResponseEntity<?> response = materialController.deleteMaterial(materiallist.get(0).getType());
        assertEquals(response.getBody(), deleted);
        verify(materialService, times(1)).deleteMaterial(materiallist.get(0).getType());
    }

    @DisplayName("Test to delete Material and return material Not Found")
    @Test
    void whenDeleteMaterialThrowException() {
        String materialId = "123";
        when(materialService.deleteMaterial(materialId)).thenThrow(new EntityNotFoundException(materialNotFound));
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> materialController.deleteMaterial(materialId));
        assertEquals(exception.getMessage(), materialNotFound);
        verify(materialService, times(1)).deleteMaterial(materialId);
    }
}
