package com.recycle.recycle.dto;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.domain.Person;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record EstablishmentDTO(
        @NotBlank(message = "Empty Name") String name,
        @NotBlank(message = "Empty Telephone") String telephone,
        @NotBlank(message = "Empty email") String email,
        @Valid @NotNull(message = "Address is required")
        AddressDTO address,
        List<Person> employees){}
