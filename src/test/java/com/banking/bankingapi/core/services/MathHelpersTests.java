package com.banking.bankingapi.core.services;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class MathHelpersTests {
    @Test
    public void roundTo2decimalPlaces_shouldReturnRoundedValue_whenValueHasMoreThan2DecimalPlaces() {
        // Assert
        var value = 5.234;
        var expectedResult = 5.23;

        // Act
        var result = MathHelpers.roundTo2decimalPlaces(value);

        // Assert
        assertEquals("should equal expected result", result, expectedResult);
    }
}
