package com.recycle.recycle.mapper;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.dto.AddressDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address convertToAddress(AddressDTO addressDTO);
    AddressDTO addressToDTO(Address address);

}
