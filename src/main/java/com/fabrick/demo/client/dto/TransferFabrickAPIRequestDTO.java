package com.fabrick.demo.client.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferFabrickAPIRequestDTO {

    public Creditor creditor;
    @JsonFormat(pattern="yyyy-MM-dd")
    public LocalDate executionDate;
    public String uri;
    public String description;
    public Long amount;
    public String currency;
    @JsonProperty("isUrgent")
    public boolean isUrgent;
    @JsonProperty("isInstant")
    public boolean isInstant;
    public String feeType;
    public String feeAccountId;
    public TaxRelief taxRelief;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Creditor{
        public String name;
        public Account account;
        public Address address;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Account{
        public String accountCode;
        public String bicCode;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Address{
        public String address;
        public String city;
        public String countryCode;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TaxRelief{
        public String taxReliefId;
        @JsonProperty("isCondoUpgrade")
        public boolean isCondoUpgrade;
        public String creditorFiscalCode;
        public String beneficiaryType;
        public NaturalPersonBeneficiary naturalPersonBeneficiary;
        public LegalPersonBeneficiary legalPersonBeneficiary;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class NaturalPersonBeneficiary{
        public String fiscalCode1;
        public String fiscalCode2;
        public String fiscalCode3;
        public String fiscalCode4;
        public String fiscalCode5;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class LegalPersonBeneficiary{
        public String fiscalCode;
        public String legalRepresentativeFiscalCode;
    }

}
