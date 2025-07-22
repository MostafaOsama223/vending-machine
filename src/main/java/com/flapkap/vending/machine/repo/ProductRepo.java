package com.flapkap.vending.machine.repo;

import com.flapkap.vending.machine.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.id > :cursor ORDER BY p.id ASC LIMIT :limit")
    List<Product> findAfter(@Param("cursor") int cursor, @Param("limit") int limit);
}
