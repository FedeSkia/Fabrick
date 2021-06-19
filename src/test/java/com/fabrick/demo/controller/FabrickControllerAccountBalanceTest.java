package com.fabrick.demo.controller;

import com.fabrick.demo.client.ApiClient;
import com.fabrick.demo.controller.dto.AccountBalanceDTO;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FabrickControllerAccountBalanceTest {

    @LocalServerPort
    Integer port;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @MockBean
    private ApiClient apiClient;
    String validAccount = "14537780";


    @Test
    public void check_balance_is_retrieved() {
        AccountBalanceDTO accountBalanceDTO = mockAPIClientForBalance();
        ResponseEntity<Double> response = testRestTemplate
                .getForEntity("http://localhost:" + port + "/balance?accountId=" + validAccount, Double.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(accountBalanceDTO.getPayload().getAvailableBalance());
    }

    @Test
    public void check_balance_when_invalid_account() {
        String invalidAccountId = "xyz";
        ResponseEntity<String> response = testRestTemplate
                .getForEntity("http://localhost:" + port + "/balance?accountId=" + invalidAccountId, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private AccountBalanceDTO mockAPIClientForBalance(){
        AccountBalanceDTO accountBalanceDTO = AccountBalanceDTO.builder()
                .error(null)
                .status("OK")
                .payload(AccountBalanceDTO.Payload.builder()
                        .balance(100)
                        .availableBalance(100)
                        .currency("EUR")
                        .build())
                .build();

        Mockito.when(apiClient.getBalanceFromFabrickAPI(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(accountBalanceDTO));
        return accountBalanceDTO;
    }


}