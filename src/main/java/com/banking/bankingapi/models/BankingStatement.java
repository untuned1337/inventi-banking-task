package com.banking.bankingapi.models;

import java.time.LocalDateTime;

public record BankingStatement(
        String accountNumber,
        LocalDateTime operationDateTime,
        String beneficiary,
        String comment,
        double amount,
        String currency) {}
