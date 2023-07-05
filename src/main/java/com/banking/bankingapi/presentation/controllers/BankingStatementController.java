package com.banking.bankingapi.presentation.controllers;

import com.banking.bankingapi.core.dto.bankingStatements.CreateBankingStatementRequest;
import com.banking.bankingapi.core.dto.common.DateRangeRequest;
import com.banking.bankingapi.core.dto.imports.ImportCsvRequest;
import com.banking.bankingapi.core.services.BankingStatementService;
import com.banking.bankingapi.core.services.csv.CsvSerializer;
import com.banking.bankingapi.core.services.csv.SerializeCsvResponse;
import com.banking.bankingapi.presentation.helpers.HttpResponseHelpers;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController()
@RequestMapping("bankingStatements")
public class BankingStatementController {
    private final BankingStatementService service;
    private final CsvSerializer csvSerializer;

    public BankingStatementController(BankingStatementService service, CsvSerializer csvSerializer){
        this.service = service;
        this.csvSerializer = csvSerializer;
    }

    @GetMapping(value = "/csv", produces = "text/csv")
    public ResponseEntity<Object> exportCsv(
            @ParameterObject DateRangeRequest dateRange) {
        var fileResponse = service.getCsv(dateRange);
        return HttpResponseHelpers.buildResponse(fileResponse);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SerializeCsvResponse<CreateBankingStatementRequest>> importCsv(
            @ModelAttribute ImportCsvRequest request) throws IOException {
        var dataBytes = request.getFile().getBytes();
        var parsedCsv = csvSerializer.deserialize(CreateBankingStatementRequest.class, dataBytes);
        if (parsedCsv.hasFailedRows() && request.isStopOnErrors()) {
            return new ResponseEntity<>(parsedCsv, HttpStatus.BAD_REQUEST);
        }

        service.createMany(parsedCsv.getSuccessfulRows());

        return new ResponseEntity<>(parsedCsv, HttpStatus.CREATED);
    }
}
