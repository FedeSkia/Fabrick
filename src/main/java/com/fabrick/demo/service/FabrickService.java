package com.fabrick.demo.service;

import com.fabrick.demo.client.ApiClient;
import com.fabrick.demo.client.dto.TransferFabrickAPIRequestDTO;
import com.fabrick.demo.client.dto.TransferFabrickAPIResponseDTO;
import com.fabrick.demo.controller.dto.TransactionsDTO;
import com.fabrick.demo.controller.dto.TransferDTO;
import com.fabrick.demo.mapper.TransactionMapper;
import com.fabrick.demo.mapper.TransferMapper;
import com.fabrick.demo.repository.Transaction;
import com.fabrick.demo.repository.TransactionRepository;
import exception.BalanceNotFoundException;
import exception.TransactionsNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FabrickService {

    private final ApiClient apiClient;
    private final TransferMapper transferMapper;
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;

    public FabrickService(ApiClient apiClient,
                          TransferMapper transferMapper,
                          TransactionMapper transactionMapper,
                          TransactionRepository transactionRepository) {
        this.apiClient = apiClient;
        this.transferMapper = transferMapper;
        this.transactionMapper = transactionMapper;
        this.transactionRepository = transactionRepository;
    }

    public double getBalanceFromAPI(String accountId) throws BalanceNotFoundException {
        return apiClient.getBalanceFromFabrickAPI(accountId)
                .orElseThrow(() -> new BalanceNotFoundException("Cant find balance for account"))
                .getPayload().getAvailableBalance();
    }

    public Collection<TransactionsDTO.Transaction> getTransactionGivenAPeriod(String accountId,
                                                                        LocalDate from,
                                                                        LocalDate to) {

        Collection<TransactionsDTO.Transaction> transactions = apiClient.getTransactionsFromFabrickAPI(accountId, from, to)
                .orElseThrow(() -> new TransactionsNotFoundException("Cant find transactions"))
                .getPayload()
                .getTransaction();
        transactionRepository.saveAll(transactions.stream()
                .map(transactionMapper::toEntity)
                .collect(Collectors.toList()));
        return transactions;
    }

    public Collection<TransferFabrickAPIResponseDTO.Error> doTransfer(TransferDTO transferDTO) {
        TransferFabrickAPIRequestDTO transferFabrickAPIRequestDTO = transferMapper.toDTO(transferDTO);
        TransferFabrickAPIResponseDTO transferResponse = apiClient.transfer(transferFabrickAPIRequestDTO);
        return transferResponse.getErrors();
    }

}
