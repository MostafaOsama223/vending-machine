package com.flapkap.vending.machine.api;

import com.flapkap.vending.machine.dto.RegisterSellerRequest;
import com.flapkap.vending.machine.service.impl.SellerServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sellers")
public class SellerController {

    private final SellerServiceImpl sellerServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterSellerRequest dto) {
        sellerServiceImpl.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
