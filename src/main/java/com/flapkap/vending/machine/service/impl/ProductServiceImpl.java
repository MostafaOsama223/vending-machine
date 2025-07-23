package com.flapkap.vending.machine.service.impl;

import com.flapkap.vending.machine.dto.CreateProductRequest;
import com.flapkap.vending.machine.dto.GetProductRequest;
import com.flapkap.vending.machine.dto.UpdateProductRequest;
import com.flapkap.vending.machine.exception.BadRequestException;
import com.flapkap.vending.machine.exception.ForbiddenException;
import com.flapkap.vending.machine.exception.ResourceNotFoundException;
import com.flapkap.vending.machine.model.Product;
import com.flapkap.vending.machine.model.Seller;
import com.flapkap.vending.machine.repo.ProductRepo;
import com.flapkap.vending.machine.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    private final SellerServiceImpl sellerServiceImpl;

    public Optional<Product> findById(int id) {
        return productRepo.findById(id);
    }

    @Override
    public List<GetProductRequest> getAll(int cursor, int limit) {

        log.info("Fetching products after cursor: {} with limit: {}", cursor, limit);

        return productRepo.findAfter(cursor, limit)
                .stream()
                .map(GetProductRequest::from)
                .toList();
    }

    @Override
    public Product getById(int id) throws IllegalStateException {
        return productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }


    @Override
    public GetProductRequest add(CreateProductRequest dto) {

        log.info("Adding new product: {}", dto.name());

        if (dto.price() % 5 != 0)
            throw new BadRequestException("Price must be a multiple of 5");

        Seller seller = sellerServiceImpl.getLoggedInUser();

        Product product = productRepo.saveAndFlush(new Product(dto.name(), dto.amount(), dto.price(), seller));

        log.info("Product added with id: {}", product.getId());

        return GetProductRequest.from(product);
    }

    @Override
    public GetProductRequest update(int id, UpdateProductRequest dto) {

        log.info("Updating product with id: {}", id);

        if (dto.price() % 5 != 0)
            throw new BadRequestException("Price must be a multiple of 5");

        Seller seller = sellerServiceImpl.getLoggedInUser();

        Product product = getById(id);

        if (!product.getSeller().getId().equals(seller.getId()))
            throw new ForbiddenException("You are not authorized to update this product");

        product.setName(dto.name());
        product.setAmount(dto.amount());
        product.setPrice(dto.price());
        productRepo.saveAndFlush(product);

        log.info("Product updated with id: {}", product.getId());

        return GetProductRequest.from(product);
    }

    @Override
    public GetProductRequest removeFromStock(int id, int amount) {

        log.info("Removing {} from stock for product with id: {}", amount, id);

        Product product = getById(id);

        if (product.getAmount() < amount)
            throw new BadRequestException("Not enough product stock available");

        if (amount <= 0)
            throw new BadRequestException("Amount must be greater than 0");

        product.setAmount(product.getAmount() - amount);
        productRepo.saveAndFlush(product);

        log.info("Removed {} from stock for product with id: {}", amount, product.getId());

        return GetProductRequest.from(product);
    }


    @Override
    public void delete(int id) {

        log.info("Deleting product with id: {}", id);

        Seller seller = sellerServiceImpl.getLoggedInUser();

        Product product = getById(id);

        if (!product.getSeller().getId().equals(seller.getId()))
            throw new ForbiddenException("You are not authorized to delete this product");

        productRepo.delete(product);

        log.info("Product deleted with id: {}", id);
    }
}
