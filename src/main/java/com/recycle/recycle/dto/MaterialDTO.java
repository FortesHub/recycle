package com.recycle.recycle.dto;

import jakarta.validation.constraints.NotBlank;

public record MaterialDTO(
        @NotBlank(message = "Empty Type") String type) {}
