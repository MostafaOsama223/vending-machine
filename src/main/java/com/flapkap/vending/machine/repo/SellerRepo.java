package com.flapkap.vending.machine.repo;

import com.flapkap.vending.machine.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepo extends JpaRepository<Seller, Integer> {

    Optional<Seller> findByUsername(String username);
}
