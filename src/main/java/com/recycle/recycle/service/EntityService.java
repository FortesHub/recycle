package com.recycle.recycle.service;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.domain.Company;
import com.recycle.recycle.domain.Establishment;
import com.recycle.recycle.repository.AddressRepository;
import com.recycle.recycle.repository.CompanyRepository;
import com.recycle.recycle.repository.EstablishmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntityService {
    private AddressRepository addressRepository;
    private CompanyRepository companyRepository;
    private EstablishmentRepository establishmentRepository;


    @Autowired
    public void addressService(AddressRepository addressRepository,
                               CompanyRepository companyRepository,
                               EstablishmentRepository establishmentRepository) {
        this.addressRepository = addressRepository;
        this.companyRepository = companyRepository;
        this.establishmentRepository = establishmentRepository;
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
}
