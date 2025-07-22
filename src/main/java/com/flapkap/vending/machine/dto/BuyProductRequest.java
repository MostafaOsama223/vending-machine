package com.flapkap.vending.machine.dto;

import jakarta.validation.constraints.Min;

public record BuyProductRequest(
        int productId,

        @Min(value = 1, message = "Amount must be at least 1")
        int amount
) {
}
