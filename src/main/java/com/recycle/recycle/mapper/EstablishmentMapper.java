package com.recycle.recycle.mapper;

import com.recycle.recycle.domain.Establishment;
import com.recycle.recycle.dto.EstablishmentDTO;
import com.recycle.recycle.mapper.utils.MappersUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EstablishmentMapper extends MappersUtils {
    @Mapping(target = "employees", ignore = true)
    @Mapping(target = "machines", ignore = true)
    Establishment convertToEstablishment(EstablishmentDTO establishmentDTO);
    @Mapping(target = "employees", expression = "java(mapEmployees(establishment.getEmployees()))")
    @Mapping(target = "machines", expression = "java(mapMachines(establishment.getMachines()))")
    EstablishmentDTO establishmentToDTO(Establishment establishment);
}



