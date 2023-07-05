package com.banking.bankingapi.infrastructure.repositories;

import com.banking.bankingapi.domain.Currency;
import com.banking.bankingapi.domain.entities.BankingStatement;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class InMemoryBankingStatementRepositoryTests {
    private InMemoryBankingStatementRepository repository;
    private final List<BankingStatement> dummyStatements = List.of(
            new BankingStatement(
                        "LT12312312",
                        LocalDateTime.now(),
                        "LT66666666",
                                "Some comment",
                                50.5,
                                Currency.EUR));

    @BeforeEach
    public void setUp() {
        repository = new InMemoryBankingStatementRepository();
    }
    @Test
    public void getAll_shouldReturnStatements() {
        // Arrange
        repository.createMany(dummyStatements);

        // Act
        var retrievedStatements = repository.getAll();

        // Assert
        assertThat(retrievedStatements, Matchers.containsInAnyOrder(dummyStatements.toArray()));
    }

    @Test
    public void createMany_shouldCreateAllGivenStatements() {
        // Act
        repository.createMany(dummyStatements);
        var retrievedStatements = repository.getAll();

        // Assert
        assertThat(retrievedStatements, Matchers.containsInAnyOrder(dummyStatements.toArray()));
    }
}
