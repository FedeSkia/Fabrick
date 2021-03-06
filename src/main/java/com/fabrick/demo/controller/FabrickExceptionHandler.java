package com.fabrick.demo.controller;

import exception.BalanceNotFoundException;
import exception.TransactionsNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class FabrickExceptionHandler {

    @ExceptionHandler({BalanceNotFoundException.class})
    public ResponseEntity<String> handleBalanceNotFound(BalanceNotFoundException e) {
        return error(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler({TransactionsNotFoundException.class})
    public ResponseEntity<String> handleTransactionNotFound(TransactionsNotFoundException e) {
        return error(HttpStatus.NOT_FOUND, e);
    }

    private ResponseEntity<String> error(HttpStatus status, Exception e) {
        log.error("Exception : ", e);
        return ResponseEntity.status(status).body(e.getMessage());
    }


}
