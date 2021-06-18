package com.fabrick.demo.controller;

import com.fabrick.demo.client.dto.TransferFabrickAPIResponseDTO;
import com.fabrick.demo.controller.dto.TransactionsDTO;
import com.fabrick.demo.controller.dto.TransferDTO;
import com.fabrick.demo.service.FabrickService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping()
@Validated
public class FabrickController {

    private final FabrickService service;

    public FabrickController(FabrickService service) {
        this.service = service;
    }

    @GetMapping("/balance")
    public ResponseEntity<Double> getBalance(@RequestParam String accountId) {
        if(valid(accountId)){
            return ResponseEntity.ok(service.getBalanceFromAPI(accountId));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/transactions")
    public ResponseEntity<Collection<TransactionsDTO.Transaction>> getTransactions(@RequestParam
                                                                     String accountId,
                                                             @RequestParam
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                     LocalDate from,
                                                             @RequestParam
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                     LocalDate to) {
        if(valid(accountId) && validDate(from) && validDate(to)){
            return ResponseEntity.ok(service.getTransactionGivenAPeriod(accountId, from, to));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/transfer")
    public Collection<TransferFabrickAPIResponseDTO.Error> doTransfer(@RequestBody TransferDTO transferDTO) {
        return service.doTransfer(transferDTO);
    }

    private boolean valid(String accountId) {
        if (accountId == null || accountId.length() == 0) {
            return false;
        }
        return true;
    }

    private boolean validDate(LocalDate date) {
        if (date.isAfter(LocalDate.now())) {
            return false;
        }
        return true;
    }

}
