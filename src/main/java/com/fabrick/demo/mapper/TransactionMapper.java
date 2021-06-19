package com.fabrick.demo.mapper;

import com.fabrick.demo.controller.dto.TransactionsDTO;
import com.fabrick.demo.repository.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Transaction toEntity(TransactionsDTO.Transaction transaction) {
        Transaction transactionEntity = new Transaction();
        transactionEntity.setTransactionId(Long.valueOf(transaction.getTransactionId()));
        transactionEntity.setOperationId(transaction.getOperationId());
        transactionEntity.setAmount(transaction.getAmount());
        transactionEntity.setCurrency(transaction.getCurrency());
        transactionEntity.setDescription(transaction.getDescription());
        transactionEntity.setEnumeration(transaction.getType().getEnumeration());
        transactionEntity.setValue(transaction.getType().getValue());
        transactionEntity.setValueDate(transaction.getValueDate());
        transactionEntity.setAccountingDate(transaction.getAccountingDate());
        return transactionEntity;
    }

}
