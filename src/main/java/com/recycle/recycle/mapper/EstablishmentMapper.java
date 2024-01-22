package com.recycle.recycle.mapper;

import com.recycle.recycle.domain.Establishment;
import com.recycle.recycle.dto.EstablishmentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EstablishmentMapper {
    Establishment convertToEstablishment(EstablishmentDTO establishmentDTO);
    EstablishmentDTO establishmentToDTO(Establishment establishment);
}



