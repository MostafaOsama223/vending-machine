package com.flapkap.vending.machine.service.impl;

import com.flapkap.vending.machine.dto.BuyProductRequest;
import com.flapkap.vending.machine.dto.BuyProductResponse;
import com.flapkap.vending.machine.dto.DepositMoneyRequest;
import com.flapkap.vending.machine.dto.RegisterBuyerRequest;
import com.flapkap.vending.machine.exception.BadRequestException;
import com.flapkap.vending.machine.exception.ResourceNotFoundException;
import com.flapkap.vending.machine.model.Buyer;
import com.flapkap.vending.machine.model.Product;
import com.flapkap.vending.machine.repo.BuyerRepo;
import com.flapkap.vending.machine.service.BuyerService;
import com.flapkap.vending.machine.service.ProductService;
import com.flapkap.vending.machine.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuyerServiceImpl implements UserService, BuyerService {

    private final BuyerRepo buyerRepo;
    private final ProductService productServiceImpl;
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Override
    public Buyer getByUsername(String username) throws IllegalStateException {
        return buyerRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Buyer not found with username: " + username));
    }

    @Override
    public Buyer getLoggedInUser() throws IllegalStateException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Buyer buyer = getByUsername(userDetails.getUsername());

        return buyer;
    }

    @Override
    public void registerBuyer(RegisterBuyerRequest dto) throws BadRequestException {
        if (dto.deposit() < 0 || dto.deposit() % 5 != 0)
            throw new BadRequestException("Deposit must be a non-negative multiple of 5");

        if (buyerRepo.existsByUsername(dto.username()))
            throw new BadRequestException("Username already exists");

        Buyer buyer = Buyer.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .deposit(dto.deposit())
                .build();

        buyerRepo.saveAndFlush(buyer);
    }

    @Override
    public int depositMoney(DepositMoneyRequest dto) {
        Buyer buyer = getLoggedInUser();

        buyer.setDeposit(buyer.getDeposit() + dto.coin().getValue());

        buyerRepo.saveAndFlush(buyer);

        return buyer.getDeposit();
    }

    @Override
    public void resetBalance() {
        Buyer buyer = getLoggedInUser();

        buyer.setDeposit(0);

        buyerRepo.saveAndFlush(buyer);
    }

    @Override
    @Transactional
    public BuyProductResponse buyProduct(BuyProductRequest dto) throws BadRequestException {
        Buyer buyer = getLoggedInUser();
        Product product = productServiceImpl.getById(dto.productId());

        if (buyer.getDeposit() < product.getPrice() * dto.amount())
            throw new BadRequestException("Not enough balance to buy the product");

        buyer.setDeposit(buyer.getDeposit() - product.getPrice() * dto.amount());
        buyerRepo.saveAndFlush(buyer);

        productServiceImpl.removeFromStock(dto.productId(), dto.amount());

        return new BuyProductResponse(
                dto.amount() * product.getPrice(),
                buyer.getDeposit(),
                product.getName()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return buyerRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Buyer not found with username: " + username));
    }
}
