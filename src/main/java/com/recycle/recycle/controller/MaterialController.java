package com.recycle.recycle.controller;

import com.recycle.recycle.domain.Material;
import com.recycle.recycle.dto.ExceptionDTO;
import com.recycle.recycle.dto.MaterialDTO;
import com.recycle.recycle.service.MaterialService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/material")
public class MaterialController {
    private MaterialService materialService;
    private String materialNotFound = "Material Not Found!";
    private String alreadyExist = "Material Already Exist";
    private String deleted = "Material deleted successfully!";

    @Autowired
    public void materialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping
    public ResponseEntity<?> registerMaterial(@Valid @RequestBody MaterialDTO materialDTO) {
        try {
            MaterialDTO newMaterial = materialService.registerMaterial(materialDTO);
            return new ResponseEntity<>(newMaterial, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            ExceptionDTO exception = new ExceptionDTO(alreadyExist, HttpStatus.CONFLICT.value());
            return ResponseEntity.status(exception.statusCode()).body(exception);
        }
    }

    @GetMapping
    public ResponseEntity<List<Material>> getAllMaterial() {
        List<Material> resultMaterial = materialService.getAllMaterial();
        return new ResponseEntity<>(resultMaterial, HttpStatus.OK);
    }

    @GetMapping("/{materialId}")
    public ResponseEntity<?> getMaterialId(@PathVariable("materialId") String materialId) {
        Optional<Material> material = materialService.getMaterialById(materialId);
        if (material.isPresent()) {
            return new ResponseEntity<>(material, HttpStatus.OK);
        }
        throw new EntityNotFoundException(materialNotFound);
    }

    @PutMapping("/{materialId}")
    public ResponseEntity<?> updateMaterial(@PathVariable("materialId") String materialId, @Valid @RequestBody MaterialDTO materialDTO) {
        try {
            MaterialDTO material = materialService.updateMaterial(materialId, materialDTO);
            return new ResponseEntity<>(materialDTO, HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            ExceptionDTO exception = new ExceptionDTO(materialNotFound, HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(exception.statusCode()).body(exception);
        }
    }

    @DeleteMapping("/{type}")
    public ResponseEntity<?> deleteMaterial(@PathVariable("type") String type) {
        boolean material = materialService.deleteMaterial(type);
        if (material) {
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        }
        throw new EntityNotFoundException(materialNotFound);
    }
}

