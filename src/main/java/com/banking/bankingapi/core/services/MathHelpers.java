package com.banking.bankingapi.core.services;

public class MathHelpers {
    public static double roundTo2decimalPlaces(double value){
        return Math.round(value * 100.0) / 100.0;
    }
}
