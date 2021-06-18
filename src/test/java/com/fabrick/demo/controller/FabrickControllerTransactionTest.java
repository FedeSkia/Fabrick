package com.fabrick.demo.controller;


import com.fabrick.demo.controller.dto.TransactionsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FabrickControllerTransactionTest {

    @LocalServerPort
    Integer port;
    @Autowired
    private TestRestTemplate testRestTemplate;
    String validAccount = "14537780";

    @Test
    public void check_transactions_are_retrieved(){
        ResponseEntity<TransactionResponseWrapper> response = testRestTemplate
                .getForEntity("http://localhost:" + port + "/transactions?" +
                        "accountId=" + validAccount +
                        "&from=2021-01-01" +
                        "&to=2021-01-02", TransactionResponseWrapper.class);

        response.getBody();
    }


    private void mockTransactionEndpoint(){

    }

    public static class TransactionResponseWrapper {

        private Collection<TransactionsDTO.Transaction> transactions;

        public TransactionResponseWrapper(Collection<TransactionsDTO.Transaction> transactions) {
            this.transactions = transactions;
        }

        public Collection<TransactionsDTO.Transaction> getTransactions() {
            return transactions;
        }

        public void setTransactions(Collection<TransactionsDTO.Transaction> transactions) {
            this.transactions = transactions;
        }
    }


}
