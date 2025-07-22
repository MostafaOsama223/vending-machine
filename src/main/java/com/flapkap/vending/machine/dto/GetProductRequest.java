package com.flapkap.vending.machine.dto;

import com.flapkap.vending.machine.model.Product;

public record GetProductRequest(
        int id,
        String name,
        int amount,
        int price
) {

    public static GetProductRequest from(Product product) {
        return new GetProductRequest(
                product.getId(),
                product.getName(),
                product.getAmount(),
                product.getPrice()
        );
    }
}
