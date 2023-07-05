package com.banking.bankingapi.presentation.controllers;

import com.banking.bankingapi.core.dto.accounts.AccountBalanceResponse;
import com.banking.bankingapi.core.dto.common.DateRangeRequest;
import com.banking.bankingapi.core.services.BankingStatementService;
import com.banking.bankingapi.presentation.helpers.HttpResponseHelpers;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("accounts")
public class AccountController {
    private final BankingStatementService bankingStatementService;

    public AccountController(BankingStatementService bankingStatementService){
        this.bankingStatementService = bankingStatementService;
    }

    @GetMapping(value = "/{accountId}/balance")
    public ResponseEntity<AccountBalanceResponse> getBalance(
            @PathVariable String accountId,
            @ParameterObject DateRangeRequest dateRange) {
        var balance = bankingStatementService.getAccountBalance(accountId, dateRange);
        return ResponseEntity.ok(balance);
    }
}
