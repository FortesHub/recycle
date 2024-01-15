package com.recycle.recycle.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressDTO(
        @Valid @NotNull(message = "The addressKey is required.")
        AddressCompositeDTO addressComposite,
        @NotBlank(message = "Empty City") String city,
        @NotBlank(message = "Empty Pays") String pays) {
    }


