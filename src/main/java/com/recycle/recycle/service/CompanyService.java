package com.recycle.recycle.service;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.domain.Company;
import com.recycle.recycle.dto.CompanyDTO;
import com.recycle.recycle.mapper.CompanyMapper;
import com.recycle.recycle.repository.AddressRepository;
import com.recycle.recycle.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;
    private AddressRepository addressRepository;
    private CompanyMapper companyMapper;

    @Autowired
    public void companyService(CompanyRepository companyRepository,
                               AddressRepository addressRepository,
                               CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
        this.companyMapper = companyMapper;
    }

    public CompanyDTO registerCompany(CompanyDTO companyDTO) {
        Company newCompany = companyMapper.convertToCompany(companyDTO);
        getAddress(newCompany);
        Company savedCompany = companyRepository.save(newCompany);
        return companyMapper.convertToDTO(savedCompany);
    }

    public List<Company> getAllCompany(){
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyById(String companyId){
        return companyRepository.findById(companyId);
    }

    public CompanyDTO updateCompany(String companyId, CompanyDTO companyDTO) {
        Company companyToUpdate = companyMapper.convertToCompany(companyDTO);
        getAddress(companyToUpdate);
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

    public Company getAddress(Company company) {
        Address existingAddress = addressRepository.findByAddressComposite(company.getAddress().getAddressComposite());
        if (existingAddress != null) {
            company.setAddress(existingAddress);
        } else {
            Address address = addressRepository.save(company.getAddress());
            company.setAddress(address);
        }
        return company;
    }
}