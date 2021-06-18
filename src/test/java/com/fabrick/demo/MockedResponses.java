package com.fabrick.demo;

import com.fabrick.demo.controller.dto.AccountBalanceDTO;
import com.fabrick.demo.controller.dto.TransactionsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class MockedResponses {

    ObjectMapper objectMapper;

    public MockedResponses(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<AccountBalanceDTO> getMockedAccountBalanceDTO() throws IOException {
        AccountBalanceDTO accountBalanceDTO = objectMapper.readValue(new File("src/test/resources/mocked-responses/account-balance.json"),
                AccountBalanceDTO.class);
        return ResponseEntity.ok(accountBalanceDTO);
    }

    public ResponseEntity<TransactionsDTO> getTransactionsDTO() throws IOException {
        TransactionsDTO accountBalanceDTO = objectMapper.readValue(new File("src/test/resources/mocked-responses/account-balance.json"),
                TransactionsDTO.class);
        return ResponseEntity.ok(accountBalanceDTO);
    }

}
