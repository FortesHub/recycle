package com.recycle.recycle.dto;


import com.recycle.recycle.domain.Person;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CompanyDTO(
        @NotBlank(message = "Empty name") String name,
        @NotBlank(message = "Empty telephone") String telephone,
        @NotBlank(message = "Empty email") String email,
        @Valid @NotNull(message = "The address is required.")
        AddressDTO address,
        List<Person> employees){}
