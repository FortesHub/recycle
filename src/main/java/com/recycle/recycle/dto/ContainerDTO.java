package com.recycle.recycle.dto;

import com.recycle.recycle.domain.Material;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ContainerDTO(
        @NotNull(message = "Material is required") Material material,
        @NotBlank(message = "Empty Volume") String volume,
        @NotBlank(message = "Empty Value") String value) {}
