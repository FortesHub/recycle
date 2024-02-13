package com.recycle.recycle.service;

import com.recycle.recycle.domain.*;
import com.recycle.recycle.dto.CompanyDTO;
import com.recycle.recycle.mapper.CompanyMapper;
import com.recycle.recycle.repository.CompanyRepository;
import com.recycle.recycle.service.utils.AddressUtils;
import com.recycle.recycle.service.utils.EstablishmentUtils;
import com.recycle.recycle.service.utils.MachineUtils;
import com.recycle.recycle.service.utils.PersonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;
    private CompanyMapper companyMapper;
    private AddressUtils addressUtils;
    private PersonUtils personUtils;
    private EstablishmentUtils establishmentUtils;
    private MachineUtils machineUtils;


    @Autowired
    public void companyService(CompanyRepository companyRepository,
                               CompanyMapper companyMapper,
                               AddressUtils addressUtils,
                               PersonUtils personUtils,
                               EstablishmentUtils establishmentUtils,
                               MachineUtils machineUtils
    ) {
//
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.addressUtils = addressUtils;
        this.personUtils = personUtils;
        this.establishmentUtils = establishmentUtils;
        this.machineUtils = machineUtils;

    }

    public CompanyDTO registerCompany(CompanyDTO companyDTO) {
        Company newCompany = companyMapper.convertToCompany(companyDTO);
        Address address = addressUtils.getAddress(newCompany.getAddress());
        newCompany.setAddress(address);
        List<Person> employees = personUtils.getEmployees(companyDTO.employees());
        newCompany.setEmployees(employees);
        List<Establishment> establishments = establishmentUtils.getEstablishments(companyDTO.establishments());
        newCompany.setEstablishments(establishments);
        List<Machine> machines = machineUtils.getMachines(companyDTO.machines());
        newCompany.setMachines(machines);
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
        Address address = addressUtils.getAddress(companyToUpdate.getAddress());
        companyToUpdate.setAddress(address);
        List<Person> employees = personUtils.getEmployees(companyDTO.employees());
        companyToUpdate.setEmployees(employees);
        List<Establishment> establishments = establishmentUtils.getEstablishments(companyDTO.establishments());
        companyToUpdate.setEstablishments(establishments);
        companyToUpdate.setCompanyId(companyId);
        List<Machine> machines = machineUtils.getMachines(companyDTO.machines());
        companyToUpdate.setMachines(machines);
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