package com.fabrick.demo.controller.dto;

import com.fabrick.demo.client.dto.Error;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountBalanceDTO {

    public String status;
    public List<Error> errors;
    public Payload payload;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Payload {

        public LocalDate date;
        public double balance;
        public double availableBalance;
        public String currency;

    }

}
