package com.banking.bankingapi.core.services;

import com.banking.bankingapi.core.dto.accounts.AccountBalanceResponse;
import com.banking.bankingapi.core.dto.bankingStatements.BankingStatementResponse;
import com.banking.bankingapi.core.dto.bankingStatements.CreateBankingStatementRequest;
import com.banking.bankingapi.core.dto.common.DateRangeRequest;
import com.banking.bankingapi.core.dto.common.FileResponse;
import com.banking.bankingapi.core.extensions.ModelMapperExtensions;
import com.banking.bankingapi.core.repositories.BankingStatementRepository;
import com.banking.bankingapi.core.services.csv.CsvSerializer;
import com.banking.bankingapi.domain.Currency;
import com.banking.bankingapi.domain.entities.BankingStatement;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BankingStatementService {
    private final BankingStatementRepository repository;
    private final ModelMapper mapper;
    private final CsvSerializer csvSerializer;
    private final CurrencyConverter currencyConverter;

    public BankingStatementService(
            BankingStatementRepository repository,
            ModelMapper mapper,
            CsvSerializer serializer,
            CurrencyConverter currencyConverter) {
        this.repository = repository;
        this.mapper = mapper;
        this.csvSerializer = serializer;
        this.currencyConverter = currencyConverter;
    }

    private List<BankingStatementResponse> mapResponses(List<BankingStatement> statements) {
        return ModelMapperExtensions.mapList(mapper, statements, BankingStatementResponse.class);
    }

    public List<BankingStatementResponse> getAll() {
        var statements = repository.getAll();
        return mapResponses(statements);
    }
    
    public FileResponse getCsv(DateRangeRequest dateRange) {
        var statements = getInDateRange(dateRange);
        var byteStream = csvSerializer.serialize(statements, BankingStatementResponse.class);
        var inputStream = new InputStreamResource(byteStream);

        var filename = "banking-statements.csv";

        return new FileResponse(inputStream, filename, "application/csv");
    }

    public List<BankingStatementResponse> getInDateRange(DateRangeRequest dateRange) {
        var statements = repository.getByFilter(
                bankingStatement -> dateRange.includes(bankingStatement.getOperationDateTime())
        );
        return mapResponses(statements);
    }

    public void createMany(List<CreateBankingStatementRequest> requests) {
        var statements = ModelMapperExtensions.mapList(mapper, requests, BankingStatement.class);
        repository.createMany(statements);
    }

    public AccountBalanceResponse getAccountBalance(String accountId, DateRangeRequest dateRange) {
        var payedAmount = repository.getByFilter(
            (statement) -> Objects.equals(statement.getAccountNumber(), accountId)
                && dateRange.includes(statement.getOperationDateTime())
        ).stream()
                .map(statement -> currencyConverter.convertToEuros(statement.getAmount(), statement.getCurrency()))
                .reduce(0.0, Double::sum);
        var benefitedAmount = repository.getByFilter(
            (statement) -> Objects.equals(statement.getBeneficiary(), accountId)
                && dateRange.includes(statement.getOperationDateTime())
        ).stream()
                .map(statement -> currencyConverter.convertToEuros(statement.getAmount(), statement.getCurrency()))
                .reduce(0.0, Double::sum);

        var balance = benefitedAmount - payedAmount;

        return new AccountBalanceResponse(balance, Currency.EUR);
    }
}
