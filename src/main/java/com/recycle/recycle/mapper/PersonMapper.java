package com.recycle.recycle.mapper;

import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.RegisterPersonDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    RegisterPersonDTO convertToDTO(Person data);
    Person convertToPerson(RegisterPersonDTO data);
    @Mapping(target = "id", ignore = true)
    Person updateDTOToPerson(RegisterPersonDTO updatedData, @MappingTarget Person person);

}

