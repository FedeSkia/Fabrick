package com.fabrick.demo.controller;

import com.fabrick.demo.MockedResponses;
import com.fabrick.demo.client.ApiClient;
import com.fabrick.demo.controller.dto.AccountBalanceDTO;
import com.fabrick.demo.controller.dto.TransactionsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FabrickControllerAccountBalanceTest {

    @LocalServerPort
    Integer port;
    @Autowired
    private TestRestTemplate testRestTemplate;

    String validAccount = "14537780";


    @Test
    public void check_balance_is_retrieved() {

        ResponseEntity<Double> response = testRestTemplate.getForEntity("http://localhost:" + port + "/balance?accountId=" + validAccount, Double.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(49441.71);
    }

    @Test
    public void check_balance_when_invalid_account() throws IOException {
        String invalidAccountId = "xyz";
        ResponseEntity<Double> response = testRestTemplate.getForEntity("http://localhost:" + port + "/balance?accountId=" + invalidAccountId, Double.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


}