package com.fabrick.demo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferDTO {

    private Long accountId;
    private String receiverName;
    private String description;
    private String currency;
    private Long amount;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate executionDate;

}
