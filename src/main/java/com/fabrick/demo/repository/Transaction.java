package com.fabrick.demo.repository;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @Column(name="transaction_id")
    private Long transactionId;

    @Column(name="operation_id")
    private String operationId;

    @Column(name="accounting_date")
    private LocalDate accountingDate;

    @Column(name="value_date")
    private LocalDate valueDate;

    @Column(name="enumeration")
    private String enumeration;

    @Column(name="value")
    private String value;

    @Column(name = "amount")
    private double amount;

    @Column(name="currency")
    private String currency;

    @Column(name="description")
    private String description;

}
