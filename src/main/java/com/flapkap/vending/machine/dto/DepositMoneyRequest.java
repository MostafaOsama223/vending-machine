package com.flapkap.vending.machine.dto;

import com.flapkap.vending.machine.CoinDenomination;

public record DepositMoneyRequest(
        CoinDenomination coin
) {
}
