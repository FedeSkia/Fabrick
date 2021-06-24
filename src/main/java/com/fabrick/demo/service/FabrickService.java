package com.fabrick.demo.service;

import com.fabrick.demo.client.ApiClient;
import com.fabrick.demo.client.dto.Error;
import com.fabrick.demo.client.dto.TransferFabrickAPIRequestDTO;
import com.fabrick.demo.client.dto.TransferFabrickAPIResponseDTO;
import com.fabrick.demo.controller.dto.TransactionsDTO;
import com.fabrick.demo.controller.dto.TransferDTO;
import com.fabrick.demo.mapper.TransactionMapper;
import com.fabrick.demo.mapper.TransferMapper;
import com.fabrick.demo.repository.TransactionRepository;
import exception.BalanceNotFoundException;
import exception.TransactionsNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
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

    public Mono<Object> getBalanceFromAPI(String accountId) throws BalanceNotFoundException {
        return apiClient.getBalanceFromFabrickAPI(accountId)
                .map(accountBalanceDTO -> {
                    if(accountBalanceDTO.getErrors() != null &&
                            !accountBalanceDTO.getErrors().isEmpty()) {
                        return accountBalanceDTO.getErrors();
                    }
                    return accountBalanceDTO.getPayload().getAvailableBalance();
                });
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

    public Collection<Error> doTransfer(TransferDTO transferDTO) {
        TransferFabrickAPIRequestDTO transferFabrickAPIRequestDTO = transferMapper.toDTO(transferDTO);
        TransferFabrickAPIResponseDTO transferResponse = apiClient.transfer(transferFabrickAPIRequestDTO);
        return transferResponse.getErrors();
    }

}
