
package com.recycle.recycle.controller;

import com.recycle.recycle.domain.Machine;
import com.recycle.recycle.dto.ExceptionDTO;
import com.recycle.recycle.dto.MachineDTO;
import com.recycle.recycle.service.MachineService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/machine")
public class MachineController {
    private MachineService machineService;
    private String notFound = "Machine Not Found!";
    private String alreadyExist = "Machine Already Exist";
    private String deleted = "Machine deleted successfully!";

    @Autowired
    public void machineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @PostMapping
    public ResponseEntity<?> registerMachine(@Valid @RequestBody MachineDTO machineDTO) {
        try {
            MachineDTO registeredMachine = machineService.registerMachine(machineDTO);
            return new ResponseEntity<>(registeredMachine, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            ExceptionDTO exception = new ExceptionDTO(alreadyExist, HttpStatus.CONFLICT.value());
            return ResponseEntity.status(exception.statusCode()).body(exception);
        }
    }

    @GetMapping
    public ResponseEntity<List<Machine>> getAllMachine() {
        return new ResponseEntity<>(machineService.getAllMachine(), HttpStatus.OK);
    }

    @GetMapping("/{machineId}")
    public ResponseEntity<?> getMachineById(@PathVariable("machineId") String machineId) {
        Machine existingMachine = machineService.getMachineById(machineId)
                .orElseThrow(() -> new EntityNotFoundException(notFound));
        return new ResponseEntity<>(existingMachine, HttpStatus.OK);
    }

    @PutMapping("/{machineId}")
    public ResponseEntity<?> updateMachine(@PathVariable("machineId") String machineId, @RequestBody MachineDTO machineDTO) {
        try {
            MachineDTO updatedMachine = machineService.updateMachine(machineId, machineDTO);
            return new ResponseEntity<>(updatedMachine, HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            ExceptionDTO exceptionDTO = new ExceptionDTO(notFound, HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(exceptionDTO.statusCode()).body(exceptionDTO);
        }
    }

    @DeleteMapping("/{machineId}")
    public ResponseEntity<?> deleteMachine(@PathVariable("machineId") String machineId){
        Boolean deletedMachine = machineService.deleteMachine(machineId);
        if(deletedMachine){
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        }
        throw new EntityNotFoundException(notFound);
    }
}
