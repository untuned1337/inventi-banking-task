package com.banking.bankingapi.controllers;

import com.banking.bankingapi.models.BankingStatement;
import com.banking.bankingapi.repositories.BankingStatementRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("bankingStatements")
public class BankingStatementController {
    @GetMapping()
    public List<BankingStatement> getAll(){
        var repository = new BankingStatementRepository();
        return repository.getAll();
    }
}
