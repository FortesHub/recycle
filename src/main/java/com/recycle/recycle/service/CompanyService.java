package com.recycle.recycle.service;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.domain.Company;
import com.recycle.recycle.dto.CompanyDTO;
import com.recycle.recycle.mapper.CompanyMapper;
import com.recycle.recycle.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;
    private EntityService entityService;
    private CompanyMapper companyMapper;

    @Autowired
    public void companyService(CompanyRepository companyRepository,
                               EntityService entityService,
                               CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.entityService = entityService;
        this.companyMapper = companyMapper;
    }

    public CompanyDTO registerCompany(CompanyDTO companyDTO) {
        Company newCompany = companyMapper.convertToCompany(companyDTO);
        Address address = entityService.getAddress(newCompany.getAddress());
        newCompany.setAddress(address);
        Company savedCompany = companyRepository.save(newCompany);
        return companyMapper.convertToDTO(savedCompany);
    }

    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyById(String companyId) {
        return companyRepository.findById(companyId);
    }

    public CompanyDTO updateCompany(String companyId, CompanyDTO companyDTO) {
        Company companyToUpdate = companyMapper.convertToCompany(companyDTO);
        Address address = entityService.getAddress(companyToUpdate.getAddress());
        companyToUpdate.setAddress(address);
        companyToUpdate.setCompanyId(companyId);
        Company updatedCompany = companyRepository.save(companyToUpdate);
        return companyMapper.convertToDTO(updatedCompany);
    }

    public Boolean deleteCompany(String companyId) {
        Optional<Company> companyToDelete = getCompanyById(companyId);
        if (companyToDelete.isPresent()) {
            companyRepository.delete(companyToDelete.get());
            return true;
        }
        return false;
    }
}