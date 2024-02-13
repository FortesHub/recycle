package com.recycle.recycle.repository;

import com.recycle.recycle.domain.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstablishmentRepository extends JpaRepository<Establishment, String> {

    List<Establishment> findByNameIgnoreCaseContaining(String name);
    List<Establishment> findByAddress_AddressComposite_PostalCodeIgnoreCaseContaining(String postalCode);
    List<Establishment> findByMachines_Containers_TypeIgnoreCase(String type);
    List<Establishment> findByNameIgnoreCaseContainingAndAddress_AddressComposite_PostalCodeIgnoreCaseContaining(String name, String postalCode);
    List<Establishment> findByNameIgnoreCaseContainingAndMachines_Containers_TypeIgnoreCase(String name, String type);
    List<Establishment> findByNameIgnoreCaseContainingAndAddress_AddressComposite_PostalCodeIgnoreCaseContainingAndMachines_Containers_TypeIgnoreCase(String name, String postalCode, String type);
    List<Establishment> findByAddress_AddressComposite_PostalCodeIgnoreCaseContainingAndMachines_Containers_TypeIgnoreCase(String postalCode, String type);
}

