package com.recycle.recycle.dto;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.domain.Person;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.ConvertGroup;

public record RegisterPersonDTO(@NotBlank(message = "Empty name")
                                String name, @NotBlank(message = "Empty telephone")
                                String telephone, @NotBlank(message = "Empty email")
                                String email, @Valid
                                @NotNull(message = "The address is required.")
                                @ConvertGroup.List({
                                        @ConvertGroup(from = Person.class, to = Address.class)
                                })
                                AddressDTO address) {


}
