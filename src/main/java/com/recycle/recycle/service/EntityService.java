package com.recycle.recycle.service;

import com.recycle.recycle.domain.*;
import com.recycle.recycle.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EntityService {
    private AddressRepository addressRepository;
    private CompanyRepository companyRepository;
    private EstablishmentRepository establishmentRepository;
    private MaterialRepository materialRepository;
    private ContainerRepository containerRepository;


    @Autowired
    public void addressService(AddressRepository addressRepository,
                               CompanyRepository companyRepository,
                               EstablishmentRepository establishmentRepository,
                               MaterialRepository materialRepository,
                               ContainerRepository containerRepository){
        this.addressRepository = addressRepository;
        this.companyRepository = companyRepository;
        this.establishmentRepository = establishmentRepository;
        this.materialRepository = materialRepository;
        this.containerRepository = containerRepository;
    }

    public Address getAddress(Address address) {
        Address existingAddress = addressRepository
                .findByAddressComposite(address.getAddressComposite());
        if (existingAddress != null) {
            return existingAddress;
        } else {
            return addressRepository.save(address);
        }
    }

    public List<Company> getCompanies(List<String> companyIds) {
        List<Company> companies = new ArrayList<>();
        if (companyIds != null) {
            for (String companyId : companyIds) {
                Company company = companyRepository.findById(companyId)
                        .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + companyId));
                companies.add(company);
            }
        }
        return companies;
    }

    public List<Establishment> getEstablishments(List<String> establishmentIds) {
        List<Establishment> establishments = new ArrayList<>();
        if (establishmentIds != null) {
            for (String establishmentId : establishmentIds) {
                Establishment establishment = establishmentRepository.findById(establishmentId)
                        .orElseThrow(() -> new EntityNotFoundException("Establishment not found with id: " + establishmentId));
                establishments.add(establishment);
            }
        }
        return establishments;
    }

    public Material getMaterial (Material material){
        Material existingMaterial = materialRepository.findByTypeIgnoreCase(material.getType());
    if(existingMaterial != null){
        return existingMaterial;
    }
        return materialRepository.save(material);
    }
    public void verifyContainerDoesNotExist(String materialId, String volume, String value) {
        if (containerRepository.existsByMaterialMaterialIdAndVolumeAndValue(materialId, volume, value)) {
            throw new DataIntegrityViolationException("Container Already Exist");
        }
    }
}
