package com.banking.bankingapi.core.services;

import com.banking.bankingapi.domain.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class CurrencyConverterTests {
    @Test
    public void convertToEuros_shouldCorrectlyConvertEurosFromEuros() {
        // Assert
        var converter = new CurrencyConverter();
        var amountInEuros = 5.0;
        var expectedResult = 5.0;

        // Act
        var result = converter.convertToEuros(amountInEuros, Currency.EUR);

        // Assert
        assertEquals("should equal expected result", result, expectedResult);
    }

    @Test
    public void convertToEuros_shouldCorrectlyConvertEurosFromUSD() {
        // Assert
        var converter = new CurrencyConverter();
        var amountInUSD = 5.0;
        var expectedResult = 5.0 * 0.92;

        // Act
        var result = converter.convertToEuros(amountInUSD, Currency.USD);

        // Assert
        assertEquals("should equal expected result", result, expectedResult);
    }
}
