package com.banking.bankingapi.core.repositories;

import com.banking.bankingapi.domain.entities.BankingStatement;

import java.util.List;
import java.util.function.Predicate;

public interface BankingStatementRepository {
    List<BankingStatement> getAll();
    List<BankingStatement> getByFilter(Predicate<? super BankingStatement> predicate);
    void createMany(List<BankingStatement> statements);
}
