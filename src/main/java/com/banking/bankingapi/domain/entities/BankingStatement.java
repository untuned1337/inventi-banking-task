package com.banking.bankingapi.domain.entities;

import com.banking.bankingapi.domain.Currency;
import com.banking.bankingapi.domain.exceptions.BadDataException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankingStatement {
    String accountNumber;

    LocalDateTime operationDateTime;

    String beneficiary;

    String comment;

    double amount;

    Currency currency;

    public void validate() {
        if(Objects.equals(accountNumber, beneficiary)){
            throw new BadDataException("Beneficiary account cannot be the same as payer account");
        }
        if(amount <= 0){
            throw new BadDataException("Amount cannot be less than or equal to 0");
        }
    }
}
