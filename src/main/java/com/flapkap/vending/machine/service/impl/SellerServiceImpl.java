package com.flapkap.vending.machine.service.impl;

import com.flapkap.vending.machine.dto.RegisterSellerRequest;
import com.flapkap.vending.machine.exception.BadRequestException;
import com.flapkap.vending.machine.exception.ResourceNotFoundException;
import com.flapkap.vending.machine.model.Seller;
import com.flapkap.vending.machine.repo.SellerRepo;
import com.flapkap.vending.machine.service.SellerService;
import com.flapkap.vending.machine.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService, UserService {

    private final SellerRepo sellerRepo;
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Override
    public Seller getByUsername(String username) throws IllegalStateException {
        return sellerRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with username: " + username));
    }

    @Override
    public Seller getLoggedInUser() throws IllegalStateException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Seller seller = this.getByUsername(userDetails.getUsername());

        return seller;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return sellerRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Seller not found with username: " + username));
    }

    @Override
    public void register(RegisterSellerRequest dto) throws BadRequestException {
        if (sellerRepo.existsByUsername(dto.username()))
            throw new BadRequestException("Seller with username " + dto.username() + " already exists.");


        Seller seller = Seller.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .build();

        sellerRepo.save(seller);
    }
}
