package com.recycle.recycle.mapper;

import com.recycle.recycle.domain.Company;
import com.recycle.recycle.domain.Person;
import com.recycle.recycle.dto.PersonDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(target = "companyIds", source = "companies", qualifiedByName = "extractCompanyIds")
    PersonDTO convertToDTO(Person person);

    @Mapping(target = "companies", source = "companyIds", qualifiedByName = "mapCompanyIdsToCompanies")
    Person convertToPerson(PersonDTO personDTO);

    // ... outros mapeamentos ...

    @Named("extractCompanyIds")
    default List<String> extractCompanyIds(List<Company> companies) {
        return companies.stream()
                .map(Company::getCompanyId)
                .collect(Collectors.toList());
    }

    @Named("mapCompanyIdsToCompanies")
    @IterableMapping(qualifiedByName = "mapCompanyIdsToCompany")
    List<Company> mapCompanyIdsToCompanies(List<String> companyIds);

    @Named("mapCompanyIdsToCompany")
    default Company mapCompanyIdsToCompany(String companyId) {
        Company company = new Company();
        company.setCompanyId(companyId);
        return company;
    }
}