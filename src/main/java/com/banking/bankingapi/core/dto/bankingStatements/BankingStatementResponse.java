package com.banking.bankingapi.core.dto.bankingStatements;

import com.banking.bankingapi.core.dto.imports.DoubleConverter;
import com.banking.bankingapi.core.dto.imports.LocalDateTimeConverter;
import com.banking.bankingapi.domain.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsefa.csv.annotation.CsvDataType;
import net.sf.jsefa.csv.annotation.CsvField;

import java.io.Serializable;
import java.time.LocalDateTime;

@CsvDataType()
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankingStatementResponse implements Serializable {
    @CsvField(pos = 1, required = true)
    String accountNumber;

    @CsvField(pos = 2, converterType = LocalDateTimeConverter.class, required = true)
    LocalDateTime operationDateTime;

    @CsvField(pos = 3, required = true)
    String beneficiary;

    @CsvField(pos = 4)
    String comment;

    @CsvField(pos = 5, converterType = DoubleConverter.class, required = true)
    double amount;

    @CsvField(pos = 6, required = true)
    Currency currency;
}
