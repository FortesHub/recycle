package com.recycle.recycle.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressCompositeDTO(
        @NotBlank(message = "Empty street") String street,
        @NotBlank(message = "Empty complement") String complement,
        @NotBlank(message = "Empty postalCode") String postalCode) {}
