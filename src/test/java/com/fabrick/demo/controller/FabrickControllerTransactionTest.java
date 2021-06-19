package com.fabrick.demo.controller;


import com.fabrick.demo.client.ApiClient;
import com.fabrick.demo.controller.dto.TransactionsDTO;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FabrickControllerTransactionTest {

    @LocalServerPort
    Integer port;
    String validAccount = "14537780";
    @Autowired
    private TestRestTemplate testRestTemplate;
    @MockBean
    private ApiClient apiClient;

    @Test
    public void check_transactions_are_retrieved() {
        TransactionsDTO transactionsDTO = mockTransactionEndpoint(LocalDate.now());
        ResponseEntity<TransactionsDTO.Transaction[]> response = testRestTemplate
                .getForEntity("http://localhost:" + port + "/transactions?" +
                        "accountId=" + validAccount +
                        "&from=2021-01-17" +
                        "&to=2021-01-17", TransactionsDTO.Transaction[].class);
        assertThat(response.getBody().length).isEqualTo(1);
        assertThat(response.getBody()[0]).isEqualTo(transactionsDTO.getPayload().getTransaction().get(0));
    }


    private TransactionsDTO mockTransactionEndpoint(LocalDate accountingDate) {

        TransactionsDTO transactionsDTO = TransactionsDTO.builder()
                .error(null)
                .status(HttpStatus.OK.toString())
                .payload(TransactionsDTO.Payload.builder()
                        .transaction(Collections.singletonList(TransactionsDTO.Transaction.builder()
                                .transactionId("1")
                                .accountingDate(accountingDate)
                                .amount(100)
                                .currency("EUR")
                                .type(TransactionsDTO.Type.builder()
                                        .enumeration("enumeration")
                                        .value("value")
                                        .build())
                                .build()))
                        .build())
                .build();

        Mockito.when(apiClient
                .getTransactionsFromFabrickAPI(ArgumentMatchers.anyString(), ArgumentMatchers.any(LocalDate.class), ArgumentMatchers.any(LocalDate.class)))
                .thenReturn(transactionsDTO);
        return transactionsDTO;
    }

}
