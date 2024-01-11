package com.recycle.recycle.repository;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.domain.AddressComposite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, AddressComposite> {
    Address findByAddressComposite(AddressComposite addressComposite);
}

