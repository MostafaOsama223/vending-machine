package com.flapkap.vending.machine.service;

import com.flapkap.vending.machine.dto.BuyProductRequest;
import com.flapkap.vending.machine.dto.BuyProductResponse;
import com.flapkap.vending.machine.dto.DepositMoneyRequest;
import com.flapkap.vending.machine.dto.RegisterBuyerRequest;
import com.flapkap.vending.machine.exception.BadRequestException;

public interface BuyerService {

    void registerBuyer(RegisterBuyerRequest dto) throws BadRequestException;

    int depositMoney(DepositMoneyRequest dto);

    void resetBalance();

    BuyProductResponse buyProduct(BuyProductRequest dto) throws BadRequestException;
}
