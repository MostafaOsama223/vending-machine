package com.flapkap.vending.machine.api;

import com.flapkap.vending.machine.dto.CreateProductRequest;
import com.flapkap.vending.machine.dto.GetProductRequest;
import com.flapkap.vending.machine.dto.UpdateProductRequest;
import com.flapkap.vending.machine.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<GetProductRequest>> getAllProducts(
            @RequestParam(name = "cursor", defaultValue = "0") int cursor,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        return ResponseEntity.ok(productService.getAll(cursor, limit));
    }

    @PostMapping
    public ResponseEntity<GetProductRequest> addProduct(@RequestBody @Valid CreateProductRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.add(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetProductRequest> updateProduct(
            @PathVariable int id,
            @RequestBody @Valid UpdateProductRequest dto) {
        return ResponseEntity.ok(productService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
