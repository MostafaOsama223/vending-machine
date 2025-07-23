package com.flapkap.vending.machine.api;

import com.flapkap.vending.machine.dto.BuyProductRequest;
import com.flapkap.vending.machine.dto.BuyProductResponse;
import com.flapkap.vending.machine.dto.DepositMoneyRequest;
import com.flapkap.vending.machine.dto.RegisterBuyerRequest;
import com.flapkap.vending.machine.service.BuyerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/buyers")
public class BuyerController {

    private final BuyerService buyerServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<Void> registerBuyer(@RequestBody @Valid RegisterBuyerRequest dto) {
        buyerServiceImpl.registerBuyer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/buy")
    public ResponseEntity<BuyProductResponse> buyProduct(@RequestBody @Valid BuyProductRequest dto) {
        return ResponseEntity.ok(buyerServiceImpl.buyProduct(dto));
    }

    @PostMapping("/deposit")
    public ResponseEntity<Integer> depositMoney(@RequestBody DepositMoneyRequest dto) {
        return ResponseEntity.ok(buyerServiceImpl.depositMoney(dto));
    }

    @PostMapping("/reset")
    public ResponseEntity<Void> resetBalance() {
        buyerServiceImpl.resetBalance();
        return ResponseEntity.ok().build();
    }
}
