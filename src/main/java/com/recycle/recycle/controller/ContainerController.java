package com.recycle.recycle.controller;

import com.recycle.recycle.domain.ContainerMaterial;
import com.recycle.recycle.dto.ContainerDTO;
import com.recycle.recycle.dto.ExceptionDTO;
import com.recycle.recycle.service.ContainerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/container")
public class ContainerController {
    private ContainerService containerService;
    private String notFound = "Container Not Found!";
    private String alreadyExist = "Container Already Exist";
    private String deleted = "Container deleted successfully!";

    @Autowired
    public void containerController(ContainerService containerService) {
        this.containerService = containerService;
    }

    @PostMapping
    public ResponseEntity<?> registerContainer(@Valid @RequestBody ContainerDTO containerDTO) {
        try {
            ContainerDTO registeredContainer = containerService.registerContainer(containerDTO);
            return new ResponseEntity<>(registeredContainer, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            ExceptionDTO exceptionDTO = new ExceptionDTO(alreadyExist, HttpStatus.CONFLICT.value());
            return ResponseEntity.status(exceptionDTO.statusCode()).body(exceptionDTO);
        }
    }

    @GetMapping
    public ResponseEntity<List<ContainerMaterial>> getAllContainer() {
        return new ResponseEntity<>(containerService.getAllContainer(), HttpStatus.OK);
    }

    @GetMapping("/{containerid}")
    public ResponseEntity<?> getContainerById(@PathVariable("containerid") String containerId) {
        Optional<ContainerMaterial> container = containerService.getContainerById(containerId);
        if (container.isPresent()) {
            return new ResponseEntity<>(container, HttpStatus.OK);
        }
        throw new EntityNotFoundException(notFound);
    }

    @PutMapping("/{containerId}")
    public ResponseEntity<?> updateContainer(@PathVariable("containerId") String containerId, @Valid @RequestBody ContainerDTO containerDTO) {
        try {
            ContainerDTO updatedContainer = containerService.updateContainer(containerId, containerDTO);
            return new ResponseEntity<>(updatedContainer, HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            ExceptionDTO exceptionDTO = new ExceptionDTO(notFound, HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(exceptionDTO.statusCode()).body(exceptionDTO);
        }
    }

        @DeleteMapping("/{containerId}")
        public ResponseEntity<?> deleteContainer (@PathVariable("containerId") String containerId){
            boolean deletedContainer = containerService.deleteContainer(containerId);
            if(deletedContainer){
                return new ResponseEntity<>(deleted, HttpStatus.OK);
        }
            throw new EntityNotFoundException(notFound);
    }

}
