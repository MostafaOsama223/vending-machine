package com.flapkap.vending.machine.service;

import com.flapkap.vending.machine.dto.BuyProductRequest;
import com.flapkap.vending.machine.dto.BuyProductResponse;
import com.flapkap.vending.machine.dto.DepositMoneyRequest;

public interface BuyerService {

    int depositMoney(DepositMoneyRequest dto);

    void resetBalance();

    BuyProductResponse buyProduct(BuyProductRequest dto) throws IllegalStateException;
}
