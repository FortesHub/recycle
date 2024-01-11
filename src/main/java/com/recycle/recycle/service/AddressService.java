package com.recycle.recycle.service;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.mapper.AddressMapper;
import com.recycle.recycle.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private AddressRepository addressRepository;
    private AddressMapper addressMapper;

    @Autowired
    public void addressService(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

public AddressDTO registerAddress(AddressDTO addressDTO){
        Address newAddress = addressMapper.convertToAddress(addressDTO);
        Address savedAddress = addressRepository.save(newAddress);
        return addressMapper.addressToDTO(savedAddress);
}

//public Optional<AddressKey> getAddressByComposite(String street, String complement, String postalCode){
//        return addressRepository;
//
//}

}
