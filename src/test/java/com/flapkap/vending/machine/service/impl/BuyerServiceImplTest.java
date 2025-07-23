package com.flapkap.vending.machine.service.impl;

import com.flapkap.vending.machine.CoinDenomination;
import com.flapkap.vending.machine.dto.DepositMoneyRequest;
import com.flapkap.vending.machine.model.Buyer;
import com.flapkap.vending.machine.repo.BuyerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class BuyerServiceImplTest {

    @Mock
    private BuyerRepo buyerRepo;

    @Spy
    @InjectMocks
    private BuyerServiceImpl buyerServiceSpy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void depositMoneyIncreasesBuyerDeposit() {
        Buyer buyer = Buyer.builder().deposit(10).build();
        doReturn(buyer).when(buyerServiceSpy).getLoggedInUser();

        DepositMoneyRequest request = new DepositMoneyRequest(CoinDenomination.CENT_5);

        int updatedDeposit = buyerServiceSpy.depositMoney(request);

        assertEquals(15, updatedDeposit);
        assertEquals(15, buyer.getDeposit());
    }

    @Test
    void resetBalanceSetsDepositToZero() {
        Buyer buyer = Buyer.builder().deposit(10).build();
        doReturn(buyer).when(buyerServiceSpy).getLoggedInUser();

        buyerServiceSpy.resetBalance();

        assertEquals(0, buyer.getDeposit());
    }

}