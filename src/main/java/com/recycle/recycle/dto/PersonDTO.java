package com.recycle.recycle.dto;

import jakarta.validation.constraints.NotBlank;

public record PersonDTO(
        @NotBlank(message = "Empty name") String name,
        @NotBlank(message = "Empty telephone") String telephone,
        @NotBlank(message = "Empty email") String email) {
}


