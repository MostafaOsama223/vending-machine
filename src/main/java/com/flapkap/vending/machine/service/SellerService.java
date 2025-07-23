package com.flapkap.vending.machine.service;

import com.flapkap.vending.machine.dto.RegisterBuyerRequest;
import com.flapkap.vending.machine.dto.RegisterSellerRequest;
import com.flapkap.vending.machine.exception.BadRequestException;

public interface SellerService {

    void register(RegisterSellerRequest dto) throws BadRequestException;
}
