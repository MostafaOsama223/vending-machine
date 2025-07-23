package com.flapkap.vending.machine.service.impl;

import com.flapkap.vending.machine.dto.CreateProductRequest;
import com.flapkap.vending.machine.dto.GetProductRequest;
import com.flapkap.vending.machine.dto.UpdateProductRequest;
import com.flapkap.vending.machine.exception.BadRequestException;
import com.flapkap.vending.machine.exception.ForbiddenException;
import com.flapkap.vending.machine.model.Product;
import com.flapkap.vending.machine.model.Seller;
import com.flapkap.vending.machine.repo.ProductRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepo productRepo;

    @Mock
    private SellerServiceImpl sellerServiceImpl;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void getAllReturnsMappedProducts() {
//            Given
        List<Product> products = List.of(
                new Product(1, "Product1", 10, 50, Seller.builder().id(1).build()),
                new Product(2, "Product2", 5, 25, Seller.builder().id(2).build())
        );
        when(productRepo.findAfter(0, 10)).thenReturn(products);

//            When
        List<GetProductRequest> result = productService.getAll(0, 10);

//            Then
        assertEquals(2, result.size());
        assertEquals("Product1", result.get(0).name());
        assertEquals("Product2", result.get(1).name());
    }

    @Test
    void addThrowsBadRequestExceptionWhenPriceNotMultipleOfFive() {
        CreateProductRequest request = new CreateProductRequest("Product", 10, 7);

        assertThrows(BadRequestException.class, () -> productService.add(request));
    }

    @Test
    void updateThrowsBadRequestExceptionWhenPriceNotMultipleOfFive() {
        UpdateProductRequest request = new UpdateProductRequest("UpdatedProduct", 5, 7);

        assertThrows(BadRequestException.class, () -> productService.update(1, request));
    }

    @Test
    void updateThrowsForbiddenExceptionWhenSellerNotAuthorized() {
        UpdateProductRequest request = new UpdateProductRequest("UpdatedProduct", 5, 50);
        Product product = new Product(1, "Product", 10, 50, Seller.builder().id(2).build());

        when(productRepo.findById(1)).thenReturn(Optional.of(product));
        when(sellerServiceImpl.getLoggedInUser()).thenReturn(Seller.builder().id(1).build());

        assertThrows(ForbiddenException.class, () -> productService.update(1, request));
    }

    @Test
    void updateUpdatesProductSuccessfullyWhenAuthorized() {
        UpdateProductRequest request = new UpdateProductRequest("UpdatedProduct", 5, 50);
        Product product = new Product(1, "Product", 10, 50, Seller.builder().id(1).build());
        Seller seller = Seller.builder().id(1).build();

        when(productRepo.findById(1)).thenReturn(Optional.of(product));
        when(sellerServiceImpl.getLoggedInUser()).thenReturn(seller);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        when(productRepo.saveAndFlush(productCaptor.capture())).thenReturn(new Product(1, request.name(), request.amount(), request.price(), seller));

        productService.update(product.getId(), request);

        Product updatedProduct = productCaptor.getValue();
        assertEquals(request.name(), updatedProduct.getName());
        assertEquals(request.amount(), updatedProduct.getAmount());
        assertEquals(request.price(), updatedProduct.getPrice());
    }

    @Test
    void removeFromStockThrowsBadRequestExceptionWhenAmountExceedsStock() {
        Product product = Product.builder().id(1).amount(10).build();
        when(productRepo.findById(product.getId())).thenReturn(Optional.of(product));

        assertThrows(BadRequestException.class, () -> productService.removeFromStock(product.getId(), 30));
    }

    @Test
    void removeFromStockThrowsBadRequestExceptionWhenAmountIsZeroOrNegative() {
        Product product = Product.builder().id(1).amount(10).build();
        when(productRepo.findById(product.getId())).thenReturn(Optional.of(product));

        assertThrows(BadRequestException.class, () -> productService.removeFromStock(product.getId(), 0));
        assertThrows(BadRequestException.class, () -> productService.removeFromStock(product.getId(), -5));
    }

    @Test
    void removeFromStockSuccessfullyRemovesFromStock() {
        Product product = Product.builder().id(1).amount(10).build();
        when(productRepo.findById(product.getId())).thenReturn(Optional.of(product));

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        when(productRepo.saveAndFlush(productCaptor.capture())).thenReturn(new Product(product.getId(), "Product", 5, 50, Seller.builder().id(1).build()));

        GetProductRequest result = productService.removeFromStock(product.getId(), 5);

        Product updatedProduct = productCaptor.getValue();
        assertEquals(5, updatedProduct.getAmount());
    }

    @Test
    void deleteThrowsForbiddenExceptionWhenSellerNotAuthorized() {
        Seller seller = Seller.builder().id(1).build();
        Product product = Product.builder().id(1).seller(seller).build();

        when(productRepo.findById(product.getId())).thenReturn(Optional.of(product));
        when(sellerServiceImpl.getLoggedInUser()).thenReturn(Seller.builder().id(2).build());

        assertThrows(ForbiddenException.class, () -> productService.delete(product.getId()));
    }

    @Test
    void deleteDeletesProductSuccessfullyWhenAuthorized() {
        Seller seller = Seller.builder().id(1).build();
        Product product = Product.builder().id(1).seller(seller).build();

        when(productRepo.findById(product.getId())).thenReturn(Optional.of(product));
        when(sellerServiceImpl.getLoggedInUser()).thenReturn(seller);

        productService.delete(product.getId());

        verify(productRepo, times(1)).delete(product);
    }

    @Test
    void findById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getById() {
    }

    @Test
    void add() {
    }

    @Test
    void update() {
    }

    @Test
    void removeFromStock() {
    }

    @Test
    void delete() {
    }
}