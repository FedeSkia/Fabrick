package com.fabrick.demo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountBalance {

    public String status;
    public List<String> error;
    public Payload payload;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Payload {

        public String date;
        public double balance;
        public double availableBalance;
        public String currency;

    }

}
