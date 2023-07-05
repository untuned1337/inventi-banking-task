package com.banking.bankingapi.core.services;

import com.banking.bankingapi.core.dto.bankingStatements.BankingStatementResponse;
import com.banking.bankingapi.core.dto.common.DateRangeRequest;
import com.banking.bankingapi.core.extensions.ModelMapperExtensions;
import com.banking.bankingapi.core.repositories.BankingStatementRepository;
import com.banking.bankingapi.core.services.csv.CsvSerializer;
import com.banking.bankingapi.domain.Currency;
import com.banking.bankingapi.domain.entities.BankingStatement;
import com.banking.bankingapi.infrastructure.repositories.InMemoryBankingStatementRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
public class BankingStatementServiceTests {
    @MockBean
    private BankingStatementRepository repository;
    @MockBean
    private CsvSerializer csvSerializer;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    CurrencyConverter currencyConverter;

    @BeforeEach
    public void setUp() {
        Mockito.when(repository.getAll()).thenReturn(dummyStatements);
        Mockito
            .when(csvSerializer.serialize(any(), eq(BankingStatementResponse.class)))
            .thenReturn(new ByteArrayInputStream(new byte[6]));
    }

    private final List<BankingStatement> dummyStatements = List.of(
            new BankingStatement(
                    "LT12312312",
                    LocalDateTime.of(2012, 4, 23, 6, 4),
                    "LT66666666",
                    "Some comment",
                    50.5,
                    Currency.EUR),
            new BankingStatement(
                    "LT12312312",
                    LocalDateTime.of(2016, 4, 23, 6, 4),
                    "LT66666666",
                    "Some comment",
                    50.5,
                    Currency.EUR));

    @Test
    public void getAll_shouldReturnStatements() {
        // Assert
        var service = new BankingStatementService(repository, modelMapper, csvSerializer, currencyConverter);
        var expectedStatements = ModelMapperExtensions.mapList(
                modelMapper, dummyStatements, BankingStatementResponse.class);

        // Act
        var retrievedStatements = service.getAll();

        // Assert
        assertThat(retrievedStatements, Matchers.containsInAnyOrder(expectedStatements.toArray()));
    }

    @Test
    public void getCsv_shouldReturnFileResponseWithCorrectFileName() {
        // Arrange
        var service = new BankingStatementService(repository, modelMapper, csvSerializer, currencyConverter);
        var dateRange = new DateRangeRequest();
        var expectedFileName = "banking-statements.csv";

        // Act
        var file = service.getCsv(dateRange);

        // Assert
        assertThat(file.getFilename(), Matchers.equalTo(expectedFileName));
    }

    @Test
    public void getCsv_shouldReturnFileResponseWithCorrectContentType() {
        // Arrange
        var service = new BankingStatementService(repository, modelMapper, csvSerializer, currencyConverter);
        var dateRange = new DateRangeRequest();
        var expectedContentType = "application/csv";

        // Act
        var file = service.getCsv(dateRange);

        // Assert
        assertThat(file.getContentType(), Matchers.equalTo(expectedContentType));
    }

    @Test
    public void getInDateRange_shouldReturnStatementsThatFallInProvidedDateRange() {
        // Arrange
        repository = new InMemoryBankingStatementRepository();
        repository.createMany(dummyStatements);
        var dateRange = new DateRangeRequest(
                LocalDateTime.of(2011, 4, 23, 6, 4),
                LocalDateTime.of(2013, 4, 23, 6, 4));
        var service = new BankingStatementService(repository, modelMapper, csvSerializer, null);
        var expectedStatements = ModelMapperExtensions.mapList(
                modelMapper, dummyStatements.subList(0, 1), BankingStatementResponse.class);

        // Act
        var statements = service.getInDateRange(dateRange);

        // Assert
        assertThat(statements, Matchers.hasSize(expectedStatements.size()));
    }

    @Test
    public void getAccountBalance_shouldReturnCorrectBalanceInEuros() {
        // Arrange
        repository = new InMemoryBankingStatementRepository();
        repository.createMany(dummyStatements);
        var accountId = "LT66666666";
        var dateRange = new DateRangeRequest();
        var service = new BankingStatementService(repository, modelMapper, csvSerializer, currencyConverter);
        var expectedBalance = 101.0;
        var expectedCurrency = Currency.EUR;

        // Act
        var response = service.getAccountBalance(accountId, dateRange);

        // Assert
        assertThat(response.getBalance(), Matchers.equalTo(expectedBalance));
        assertThat(response.getCurrency(), Matchers.equalTo(expectedCurrency));
    }
}
