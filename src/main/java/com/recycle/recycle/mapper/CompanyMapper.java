package com.recycle.recycle.mapper;

import com.recycle.recycle.domain.Company;
import com.recycle.recycle.dto.CompanyDTO;
import com.recycle.recycle.mapper.utils.MappersUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyMapper extends MappersUtils{
    @Mapping(target = "employees", expression = "java(mapEmployees(company.getEmployees()))")
    @Mapping(target = "establishments", expression = "java(mapEstablishments(company.getEstablishments()))")
    @Mapping(target = "machines", expression = "java(mapMachines(company.getMachines()))")
    CompanyDTO convertToDTO(Company company);

    @Mapping(target = "employees", ignore = true)
    @Mapping(target = "establishments", ignore = true)
    @Mapping(target = "machines", ignore = true)
    Company convertToCompany(CompanyDTO companyDTO);


}
