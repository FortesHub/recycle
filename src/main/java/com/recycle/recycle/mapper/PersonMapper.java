package com.recycle.recycle.mapper;

import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.RegisterPersonDTO;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    RegisterPersonDTO personToPersonDTO(Person data);
    Person personDTOToPerson(RegisterPersonDTO data);
    void updatePersonFromDTO(RegisterPersonDTO updatedData, @MappingTarget Person id);
}
