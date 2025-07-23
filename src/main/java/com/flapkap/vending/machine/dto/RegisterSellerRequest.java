package com.flapkap.vending.machine.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record RegisterSellerRequest(
        @NotBlank
        @NotNull
        @Length(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
        String username,

        @NotBlank
        @NotNull
        @Length(min = 6, message = "Password must be at least 6 characters long")
        String password
) {
}
