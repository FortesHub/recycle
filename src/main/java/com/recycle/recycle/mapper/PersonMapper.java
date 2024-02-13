package com.recycle.recycle.mapper;

import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.PersonDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper  {
    PersonDTO convertToDTO(Person person);
    Person convertToPerson(PersonDTO personDTO);
}