package com.fabrick.demo.client;

import com.fabrick.demo.client.dto.TransferFabrickAPIRequestDTO;
import com.fabrick.demo.client.dto.TransferFabrickAPIResponseDTO;
import com.fabrick.demo.controller.dto.AccountBalanceDTO;
import com.fabrick.demo.controller.dto.TransactionsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

@Component
@Slf4j
public class ApiClient {

    public static final String API_URL = "https://sandbox.platfr.io";
    public static final String API_KEY = "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP";
    private final RestTemplate restTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    public ApiClient() {
        this.restTemplate  = new RestTemplate();
    }

    public Optional<AccountBalanceDTO> getBalanceFromFabrickAPI(String accountId){
        String accountUri = "/api/gbs/banking/v4.0/accounts/" + accountId + "/balance";
        try {
            return Optional.ofNullable(restTemplate.exchange(API_URL + accountUri,
                    HttpMethod.GET,
                    new HttpEntity(getHeaders()),
                    AccountBalanceDTO.class).getBody());
        } catch (HttpClientErrorException ex){
            log.error(ex.getMessage());
            return Optional.empty();
        }
    }

    public Optional<TransactionsDTO> getTransactionsFromFabrickAPI(String accountId, LocalDate from, LocalDate to) {
        String transactionsURI = "/api/gbs/banking/v4.0/accounts/" + accountId + "/transactions?fromAccountingDate=" + from.toString()
                + "&toAccountingDate=" + to.toString();
        try {
            return Optional.ofNullable(restTemplate.exchange(API_URL + transactionsURI,
                    HttpMethod.GET,
                    new HttpEntity(getHeaders()),
                    TransactionsDTO.class).getBody());
        } catch (HttpClientErrorException ex){
            return Optional.empty();
        }
    }

    @SneakyThrows
    public TransferFabrickAPIResponseDTO transfer(TransferFabrickAPIRequestDTO transferDTO){
        String transferURI = "/api/gbs/banking/v4.0/accounts/" + transferDTO.getFeeAccountId() +
                "/payments/money-transfers";
        ResponseEntity<TransferFabrickAPIResponseDTO> response;
        try {
            response = restTemplate.exchange(API_URL + transferURI,
                    HttpMethod.POST,
                    createBodyWithHeaders(transferDTO),
                    TransferFabrickAPIResponseDTO.class);
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            log.error(ex.getMessage());
            return objectMapper.readValue(ex.getResponseBodyAsString(), TransferFabrickAPIResponseDTO.class);
        }
    }

    private HttpEntity createBodyWithHeaders(TransferFabrickAPIRequestDTO transferDTO) {
        return new HttpEntity<>(transferDTO, getHeaders());
    }

    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Auth-Schema", "S2S");
        headers.set("Api-Key", API_KEY);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }


}
