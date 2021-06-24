package com.fabrick.demo.controller;

import com.fabrick.demo.controller.dto.AccountBalanceDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FabrickControllerAccountBalanceTest {

    @LocalServerPort
    Integer port;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    FabrickController fabrickController;

    String validAccount = "14537780";

    public static MockWebServer mockBackEnd;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();

        mockBackEnd.start();

        HttpUrl baseUrl = mockBackEnd.url("/api/gbs/banking/v4.0/accounts/14537780/balance");
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Test
    public void check_balance_is_retrieved() throws JsonProcessingException {
        AccountBalanceDTO response = mockAPIClientForBalance();
        WebTestClient webTestClient = WebTestClient.bindToController(fabrickController)
                .build();
        webTestClient
                .get().uri("/balance?accountId=" + validAccount)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
        .expectBody()
                .jsonPath("$").isEqualTo(String.valueOf(response.getPayload().getAvailableBalance()));
    }

    @Test
    public void check_balance_when_invalid_account() {
        String invalidAccountId = "xyz";
        WebTestClient webTestClient = WebTestClient.bindToController(fabrickController)
                .build();
        webTestClient.get().uri("/balance?accountId=" + invalidAccountId)
                .exchange()
                .expectStatus()
                .is4xxClientError();

    }

    private AccountBalanceDTO mockAPIClientForBalance() throws JsonProcessingException {
        AccountBalanceDTO accountBalanceDTO = AccountBalanceDTO.builder()
                .errors(null)
                .status("OK")
                .payload(AccountBalanceDTO.Payload.builder()
                        .balance(100)
                        .availableBalance(100)
                        .currency("EUR")
                        .build())
                .build();

        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(accountBalanceDTO)));

        return accountBalanceDTO;
    }


}