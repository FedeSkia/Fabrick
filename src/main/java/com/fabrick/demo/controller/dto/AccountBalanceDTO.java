package com.fabrick.demo.controller.dto;

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
public class AccountBalanceDTO {

    public String status;
    public List<String> error;
    public Payload payload;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Payload {

        public LocalDate date;
        public double balance;
        public double availableBalance;
        public String currency;

    }

}
