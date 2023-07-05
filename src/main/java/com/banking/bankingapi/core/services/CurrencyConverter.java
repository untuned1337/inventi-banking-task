package com.banking.bankingapi.core.services;

import com.banking.bankingapi.domain.Currency;
import org.springframework.stereotype.Service;

import java.util.Dictionary;
import java.util.Hashtable;

@Service
public class CurrencyConverter {
    private final Dictionary<Currency, Double> multipliersByCurrency;

    public CurrencyConverter() {
        Currency baseCurrency = Currency.EUR;

        multipliersByCurrency = new Hashtable<>();
        multipliersByCurrency.put(baseCurrency, 1.0);
        multipliersByCurrency.put(Currency.USD, 0.92);
    }

    public double convertToEuros(double amount, Currency currency) {
        var multiplier = multipliersByCurrency.get(currency);
        return multiplier * amount;
    }
}
