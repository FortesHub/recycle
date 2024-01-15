package com.recycle.recycle.controller;

import com.recycle.recycle.domain.Company;
import com.recycle.recycle.dto.CompanyDTO;
import com.recycle.recycle.dto.ExceptionDTO;
import com.recycle.recycle.service.CompanyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/company")
@RestController()
public class CompanyController {

    private CompanyService companyService;
    private String notFound = "Company Not Found!";
    private String alreadyExist = "Company Already Exist";
    private String deleted = "Company deleted successfully!";

    @Autowired
    public void companyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<?> registerCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        try {
            CompanyDTO registredCompany = companyService.registerCompany(companyDTO);
            return new ResponseEntity<>(registredCompany, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            ExceptionDTO exception = new ExceptionDTO(alreadyExist, HttpStatus.CONFLICT.value());
            return ResponseEntity.status(exception.statusCode()).body(exception);
        }
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompany() {
        return new ResponseEntity<>(companyService.getAllCompany(), HttpStatus.OK);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<?> getCompanyById(@PathVariable("companyId") String companyId) {
        Optional<Company> existingCompany = companyService.getCompanyById(companyId);
        if (existingCompany.isPresent()) {
            return new ResponseEntity<>(existingCompany, HttpStatus.OK);
        }
        throw new EntityNotFoundException(notFound);
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<?> updateCompany(@PathVariable("companyId") String companyId, @RequestBody CompanyDTO companyDTO) {
        try {
            CompanyDTO existingCompany = companyService.updateCompany(companyId, companyDTO);
            return new ResponseEntity<>(existingCompany, HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            ExceptionDTO exception = new ExceptionDTO(notFound, HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(exception.statusCode()).body(exception);
        }
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<?> deleteCompany(@PathVariable("companyId") String companyId) {
        Boolean wasDeleted = companyService.deleteCompany(companyId);
        if (wasDeleted) {
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        }
        throw new EntityNotFoundException(notFound);
    }
}

