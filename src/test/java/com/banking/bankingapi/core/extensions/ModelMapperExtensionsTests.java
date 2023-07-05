package com.banking.bankingapi.core.extensions;

import com.banking.bankingapi.core.dto.bankingStatements.BankingStatementResponse;
import com.banking.bankingapi.domain.Currency;
import com.banking.bankingapi.domain.entities.BankingStatement;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class ModelMapperExtensionsTests {
    @Test
    public void mapList_shouldCorrectlyMapList() {
        // Arrange
        var mapper = new ModelMapper();
        var entities = List.of(
            new BankingStatement(
                "LT1223412",
                LocalDateTime.of(2013, 4, 13, 20, 13),
                "LT66666666",
                "Some comment",
                50.5,
                Currency.EUR),
            new BankingStatement(
                "LT12312312",
                LocalDateTime.of(2012, 4, 23, 6, 4),
                "LT611166666",
                "Some comment",
                50.5,
                Currency.USD));
        var expectedResult = List.of(
            new BankingStatementResponse(
                    "LT1223412",
                    LocalDateTime.of(2013, 4, 13, 20, 13),
                    "LT66666666",
                    "Some comment",
                    50.5,
                    Currency.EUR),
            new BankingStatementResponse(
                    "LT12312312",
                    LocalDateTime.of(2012, 4, 23, 6, 4),
                    "LT611166666",
                    "Some comment",
                    50.5,
                    Currency.USD));

        // Act
        var result = ModelMapperExtensions.mapList(mapper, entities, BankingStatementResponse.class);

        // Assert
        assertThat(result, Matchers.containsInAnyOrder(expectedResult.toArray()));
    }
}
