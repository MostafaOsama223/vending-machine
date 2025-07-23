package com.flapkap.vending.machine.dto;

import com.flapkap.vending.machine.enums.CoinDenomination;

public record DepositMoneyRequest(
        CoinDenomination coin
) {
}
