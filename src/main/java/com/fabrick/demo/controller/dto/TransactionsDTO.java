package com.fabrick.demo.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionsDTO {

    public String status;
    public Collection<String> error;
    public Payload payload;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Type{
        public String enumeration;
        public String value;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Transaction {
        public String transactionId;
        public String operationId;
        public LocalDate accountingDate;
        public LocalDate valueDate;
        public Type type;
        public double amount;
        public String currency;
        public String description;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Payload{
        @JsonProperty("list")
        public List<Transaction> transaction;
    }

}
