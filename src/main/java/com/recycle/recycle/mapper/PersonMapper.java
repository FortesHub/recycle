package com.recycle.recycle.mapper;

import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.personDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    personDTO convertToDTO(Person data);
    Person convertToPerson(personDTO data);
    @Mapping(target = "id", ignore = true)
    Person updateDTOToPerson(personDTO updatedData, @MappingTarget Person person);

}

