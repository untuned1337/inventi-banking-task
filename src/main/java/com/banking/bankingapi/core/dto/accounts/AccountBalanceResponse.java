package com.banking.bankingapi.core.dto.accounts;

import com.banking.bankingapi.domain.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountBalanceResponse {
    private double balance;
    private Currency currency;
}
