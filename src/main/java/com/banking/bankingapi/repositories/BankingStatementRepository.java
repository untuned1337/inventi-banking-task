package com.banking.bankingapi.repositories;

import com.banking.bankingapi.models.BankingStatement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BankingStatementRepository {
    public List<BankingStatement> getAll(){
        var statements = new ArrayList<BankingStatement>();
        statements.add(new BankingStatement(
                "LT123132",
                LocalDateTime.now(),
                "BeneficiaryAccNum1",
                "",
                4.53,
                "EUR"));
        statements.add(new BankingStatement(
                "LT123132",
                LocalDateTime.now(),
                "BeneficiaryAccNum1",
                "",
                4.53,
                "EUR"));
        statements.add(new BankingStatement(
                "LT123132",
                LocalDateTime.now(),
                "BeneficiaryAccNum1",
                "",
                4.53,
                "EUR"));

        return statements;
    }
}
