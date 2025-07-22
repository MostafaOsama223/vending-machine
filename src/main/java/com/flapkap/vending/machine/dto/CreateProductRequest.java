package com.flapkap.vending.machine.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProductRequest(
        @NotNull @NotBlank String name,
        @Min(value = 1, message = "Amount must be at least 1") int amount,
        @Min(value = 1, message = "Price must be at least 1") int price
) {
}
