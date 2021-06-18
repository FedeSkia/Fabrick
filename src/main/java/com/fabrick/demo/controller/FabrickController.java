package com.fabrick.demo.controller;

import com.fabrick.demo.controller.dto.AccountBalance;
import com.fabrick.demo.controller.dto.TransactionsDTO;
import com.fabrick.demo.controller.dto.TransferDTO;
import com.fabrick.demo.service.FabrickService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping()
public class FabrickController {

    private final FabrickService service;

    public FabrickController(FabrickService service) {
        this.service = service;
    }

    @GetMapping("/balance")
    public double getBalance(@RequestParam String accountId){
        return service.getBalanceFromAPI(accountId);
    }

    @GetMapping("/transactions")
    public List<TransactionsDTO.Transaction> getBalance(@RequestParam
                                                        String accountId,
                                                        @RequestParam
                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                        LocalDate from,
                                                        @RequestParam
                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                        LocalDate to){
        return service.getTransactionGivenAPeriod(accountId, from, to);
    }

    @PostMapping("/transfer")
    public void doTransfer(@RequestBody TransferDTO transferDTO) {
        service.doTransfer(transferDTO);
    }


}
