package com.recycle.recycle.controller;

import com.recycle.recycle.domain.Company;
import com.recycle.recycle.dto.CompanyDTO;
import com.recycle.recycle.service.CompanyService;
import com.recycle.recycle.service.PersonService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    public void companyController(CompanyService companyService, PersonService personService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<CompanyDTO> registerCompany(@Valid @RequestBody CompanyDTO companyDTO) {
            CompanyDTO registredCompany = companyService.registerCompany(companyDTO);
            return new ResponseEntity<>(registredCompany, HttpStatus.CREATED);
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
        throw new EntityNotFoundException("Company Not Found");
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable("companyId") String companyId, @RequestBody CompanyDTO companyDTO) {
        CompanyDTO existingCompany = companyService.updateCompany(companyId, companyDTO);
        if (existingCompany == null) {
            throw new EntityNotFoundException("Company not Found");
        }
        return new ResponseEntity<>(existingCompany, HttpStatus.OK);
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<?> deleteCompany(@PathVariable("companyId") String companyId){
        Boolean wasDeleted = companyService.deleteCompany(companyId);
        if(!wasDeleted){
            throw new EntityNotFoundException("Person Not Found");
        }return new ResponseEntity<>("Company deleted successfully", HttpStatus.OK);
    }
}

