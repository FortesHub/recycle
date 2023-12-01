package com.recycle.recycle.mapper;

import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.PersonDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonDTO convertToDTO(Person person);

    Person convertToPerson(PersonDTO personDTO);

    @Mapping(target = "id", ignore = true)
    Person updateDTOToPerson(PersonDTO personDTO, @MappingTarget Person person);


}

