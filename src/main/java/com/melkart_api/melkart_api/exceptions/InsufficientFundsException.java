package com.melkart_api.melkart_api.exceptions;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }

    public InsufficientFundsException(BigDecimal currentBalance, BigDecimal requiredAmount) {
        super(String.format("Insufficient funds. Current balance: %s, Required amount: %s",
                currentBalance, requiredAmount));
    }
}