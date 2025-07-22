package com.flapkap.vending.machine.dto;

public record BuyProductResponse(
        int totalSpent,
        int totalChange,
        String productName
) {
}
