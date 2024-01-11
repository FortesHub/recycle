package com.recycle.recycle.mapper;

import com.recycle.recycle.domain.Company;
import com.recycle.recycle.dto.CompanyDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyDTO convertToDTO(Company company);
    Company convertToCompany(CompanyDTO companyDTO);
}
