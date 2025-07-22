package com.flapkap.vending.machine.service;

import com.flapkap.vending.machine.dto.CreateProductRequest;
import com.flapkap.vending.machine.dto.GetProductRequest;
import com.flapkap.vending.machine.dto.UpdateProductRequest;
import com.flapkap.vending.machine.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Product> findById(int id);

    List<GetProductRequest> getAll(int cursor, int limit);

    Product getById(int id) throws IllegalStateException;

    GetProductRequest add(CreateProductRequest dto);

    GetProductRequest update(int id, UpdateProductRequest dto);

    GetProductRequest removeFromStock(int id, int amount);

    void delete(int id);
}
