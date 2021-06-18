package com.fabrick.demo.service;

import com.fabrick.demo.client.ApiClient;
import com.fabrick.demo.client.dto.TransferFabrickAPIRequestDTO;
import com.fabrick.demo.client.dto.TransferFabrickAPIResponseDTO;
import com.fabrick.demo.controller.dto.TransactionsDTO;
import com.fabrick.demo.controller.dto.TransferDTO;
import com.fabrick.demo.mapper.TransferMapper;
import com.fabrick.demo.repository.Transaction;
import com.fabrick.demo.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FabrickService {

    private final ApiClient apiClient;
    private final TransferMapper transferMapper;
    private final TransactionRepository transactionRepository;

    public FabrickService(ApiClient apiClient,
                          TransferMapper transferMapper,
                          TransactionRepository transactionRepository) {
        this.apiClient = apiClient;
        this.transferMapper = transferMapper;
        this.transactionRepository = transactionRepository;
    }

    public double getBalanceFromAPI(String accountId) throws Exception {
        return apiClient.getBalanceFromFabrickAPI(accountId)
                .orElseThrow(() -> new Exception("Cant find balance for account"))
                .getPayload().getAvailableBalance();
    }

    public List<TransactionsDTO.Transaction> getTransactionGivenAPeriod(String accountId,
                                                                        LocalDate from,
                                                                        LocalDate to) {

        TransactionsDTO transactionsDTO = apiClient.getTransactionsFromFabrickAPI(accountId, from, to);
        List<TransactionsDTO.Transaction> transactions = transactionsDTO.getPayload().getTransaction();
        transactionRepository.saveAll(transactions.stream()
                .map(transaction -> {
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
                }).collect(Collectors.toList()));
        return transactionsDTO.getPayload().getTransaction();
    }

    public Collection<TransferFabrickAPIResponseDTO.Error> doTransfer(TransferDTO transferDTO) {
        TransferFabrickAPIRequestDTO transferFabrickAPIRequestDTO = transferMapper.toDTO(transferDTO);
        TransferFabrickAPIResponseDTO transferResponse = apiClient.transfer(transferFabrickAPIRequestDTO);
        return transferResponse.getErrors();
    }

}
