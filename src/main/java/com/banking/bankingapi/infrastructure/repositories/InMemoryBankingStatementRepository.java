package com.banking.bankingapi.infrastructure.repositories;

import com.banking.bankingapi.domain.entities.BankingStatement;
import com.banking.bankingapi.core.repositories.BankingStatementRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Repository
public class InMemoryBankingStatementRepository implements BankingStatementRepository {
    private final List<BankingStatement> statements;

    public InMemoryBankingStatementRepository() {
        statements = new ArrayList<>();
    }

    public List<BankingStatement> getAll(){
        return statements;
    }

    @Override
    public List<BankingStatement> getByFilter(Predicate<? super BankingStatement> predicate) {
        return statements.stream().filter(predicate).toList();
    }

    public void createMany(List<BankingStatement> statements) {
        statements.forEach(BankingStatement::validate);
        this.statements.addAll(statements);
    }
}
