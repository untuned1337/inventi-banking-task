package com.banking.bankingapi.core.services.csv;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class SerializeCsvResponse<T> implements Serializable {
    List<T> successfulRows;

    List<Integer> failedRowIndexes;

    public boolean hasFailedRows() {
        return !failedRowIndexes.isEmpty();
    }

    public SerializeCsvResponse(List<T> successfulRows, List<Integer> failedRowIndexes) {
        this.successfulRows = successfulRows;
        this.failedRowIndexes = failedRowIndexes;
    }
}
