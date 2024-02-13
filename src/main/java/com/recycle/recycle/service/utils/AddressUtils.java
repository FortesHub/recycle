package com.recycle.recycle.service.utils;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressUtils {
    private AddressRepository addressRepository;

    @Autowired
    public void addressUtils(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address getAddress(Address address) {
        Address existingAddress = addressRepository
                .findByAddressComposite(address.getAddressComposite());
        if (existingAddress != null) {
            return existingAddress;
        }
        return address;
    }
}
