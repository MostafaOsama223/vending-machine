package com.flapkap.vending.machine.enums;

public enum CoinDenomination {
    CENT_5(5),
    CENT_10(10),
    CENT_20(20),
    CENT_50(50),
    CENT_100(100);

    private final int value;

    CoinDenomination(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
