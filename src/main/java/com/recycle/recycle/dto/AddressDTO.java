package com.recycle.recycle.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressDTO(@NotBlank(message = "Empty Street")
                         String street, String complement, @NotBlank(message = "Empty Postal Code")
                         String postalCode, @NotBlank(message = "Empty City")
                         String city, @NotBlank(message = "Empty Pays")
                         String pays) {
}

