package com.fabrick.demo.service;

import com.fabrick.demo.client.ApiClient;
import com.fabrick.demo.client.dto.TransferFabrickAPIRequestDTO;
import com.fabrick.demo.client.dto.TransferFabrickAPIResponseDTO;
import com.fabrick.demo.client.dto.TransferResponseDTO;
import com.fabrick.demo.controller.dto.TransactionsDTO;
import com.fabrick.demo.controller.dto.TransferDTO;
import com.fabrick.demo.mapper.TransferMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class FabrickService {

    private final ApiClient apiClient;
    private final TransferMapper transferMapper;

    public FabrickService(ApiClient apiClient, TransferMapper transferMapper) {
        this.apiClient = apiClient;
        this.transferMapper = transferMapper;
    }

    public double getBalanceFromAPI(String accountId){
        return apiClient.getBalanceFromFabrickAPI(accountId).getPayload().getAvailableBalance();
    }

    public List<TransactionsDTO.Transaction> getTransactionGivenAPeriod(String accountId,
                                                                        LocalDate from,
                                                                        LocalDate to){

        TransactionsDTO transactionsDTO = apiClient.getTransactionsFromFabrickAPI(accountId, from, to);
        return transactionsDTO.getPayload().getTransaction();
    }

    public Collection<TransferFabrickAPIResponseDTO.Error> doTransfer(TransferDTO transferDTO) {
        TransferFabrickAPIRequestDTO transferFabrickAPIRequestDTO = transferMapper.toDTO(transferDTO);
        TransferFabrickAPIResponseDTO transferResponse = apiClient.transfer(transferFabrickAPIRequestDTO);
        return transferResponse.getErrors();
    }

}
