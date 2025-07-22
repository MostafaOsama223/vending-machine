package com.flapkap.vending.machine.repo;

import com.flapkap.vending.machine.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuyerRepo extends JpaRepository<Buyer, Integer> {

    Optional<Buyer> findByUsername(String username);
}
