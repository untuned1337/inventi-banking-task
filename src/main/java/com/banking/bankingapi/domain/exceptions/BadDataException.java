package com.banking.bankingapi.domain.exceptions;

public class BadDataException extends RuntimeException {
    public BadDataException(String errorMessage) {
        super(errorMessage);
    }
}
