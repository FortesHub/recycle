package com.recycle.recycle.controller;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.domain.Establishment;
import com.recycle.recycle.domain.Material;
import com.recycle.recycle.dto.EstablishmentDTO;
import com.recycle.recycle.dto.ExceptionDTO;
import com.recycle.recycle.service.EstablishmentService;
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
@RequestMapping("/establishment")
public class EstablishmentController {

    private EstablishmentService establishmentService;
    private String notFound = "establishment Not Found!";
    private String alreadyExist = "Establishment Already Exist";
    private String deleted = "establishment deleted successfully!";

    @Autowired
    public void establishmentController(EstablishmentService establishmentService) {
        this.establishmentService = establishmentService;
    }

    @PostMapping
    public ResponseEntity<?> registerEstablishment(@Valid @RequestBody EstablishmentDTO establishmentDTO) {
        try {
            EstablishmentDTO registeredEstablishment = establishmentService.registerEstablishment(establishmentDTO);
            return new ResponseEntity<>(registeredEstablishment, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            ExceptionDTO exceptionDTO = new ExceptionDTO(alreadyExist, HttpStatus.CONFLICT.value());
            return ResponseEntity.status(exceptionDTO.statusCode()).body(exceptionDTO);
        }
    }

    @GetMapping
    public ResponseEntity<List<Establishment>> getAllEstablishment() {
        List<Establishment> establishments = establishmentService.getAllEstablishment();
        return new ResponseEntity<>(establishments, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getEstablishmentByFilters(@RequestParam(name = "name", required = false) String name,
                                                       @RequestParam(name = "postalCode", required = false) String postalCode,
                                                       @RequestParam(name = "type", required = false) String type){
        List<Establishment> establishments = establishmentService.getEstablishmentByFilters(name, postalCode, type);
        return new ResponseEntity<>(establishments, HttpStatus.OK);
    }


    @GetMapping("/{establishmentId}")
    public ResponseEntity<?> getEstablishmentById(@PathVariable("establishmentId") String establishmentId) {
      Establishment existingEstablishment = establishmentService.getEstablishmentByID(establishmentId)
              .orElseThrow(() -> new EntityNotFoundException(notFound));
            return new ResponseEntity<>(existingEstablishment, HttpStatus.OK);
    }

    @PutMapping("/{establishmentId}")
    public ResponseEntity<?> updateEstablishment(@PathVariable("establishmentId") String establishmentId, @RequestBody EstablishmentDTO establishmentDTO) {
        try {
            EstablishmentDTO establishment = establishmentService.updateEstablishment(establishmentId, establishmentDTO);
            return new ResponseEntity<>(establishment, HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            ExceptionDTO exception = new ExceptionDTO(notFound, HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(exception.statusCode()).body(exception);
        }
    }

    @DeleteMapping("/{establishmentId}")
    public ResponseEntity<?> deleteEstablishment(@PathVariable("establishmentId") String establishmentId) {
        boolean wasDeleted = establishmentService.deleteEstablishment(establishmentId);
        if (wasDeleted) {
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        }
        throw new EntityNotFoundException(notFound);
    }
}
