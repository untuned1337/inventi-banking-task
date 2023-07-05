package com.banking.bankingapi.domain.entities;

import com.banking.bankingapi.domain.exceptions.BadDataException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

public class BankingStatementTests {
    @Test
    public void validate_shouldThrowBadDataException_whenAccountNumberIsTheSameAsBeneficiary() {
        // Arrange
        var statement = new BankingStatement();
        statement.setAccountNumber("LT1111");
        statement.setBeneficiary("LT1111");
        var expectedMessage = "Beneficiary account cannot be the same as payer account";

        // Assert
        var exception = assertThrows(BadDataException.class, statement::validate);
        assertEquals(exception.getMessage(), expectedMessage);
    }

    @Test
    public void validate_shouldThrowBadDataException_whenAmountIsLessOrEqualTo0() {
        // Arrange
        var statement = new BankingStatement();
        statement.setAccountNumber("LT2");
        statement.setBeneficiary("LT3");
        statement.setAmount(-5);
        var expectedMessage = "Amount cannot be less than or equal to 0";

        // Assert
        var exception = assertThrows(BadDataException.class, statement::validate);
        assertEquals(exception.getMessage(), expectedMessage);
    }
}
