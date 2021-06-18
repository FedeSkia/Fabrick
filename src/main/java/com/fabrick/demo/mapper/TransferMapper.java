package com.fabrick.demo.mapper;

import com.fabrick.demo.client.dto.TransferFabrickAPIRequestDTO;
import com.fabrick.demo.controller.dto.TransferDTO;
import org.springframework.stereotype.Component;

@Component
public class TransferMapper {

    public TransferFabrickAPIRequestDTO toDTO(TransferDTO transferDTO) {
        return TransferFabrickAPIRequestDTO.builder()
                .creditor(TransferFabrickAPIRequestDTO.Creditor.builder()
                        .account(TransferFabrickAPIRequestDTO.Account.builder()
                                .accountCode("IT23A0336844430152923804660")
                                .bicCode("SELBIT2BXXX")
                                .build())
                        .address(TransferFabrickAPIRequestDTO.Address.builder()
                                .address(null)
                                .city(null)
                                .countryCode(null)
                                .build())
                        .name("John Doe")
                        .build())
                .executionDate(transferDTO.getExecutionDate())
                .uri("REMITTANCE_INFORMATION")
                .description("Payment invoice 75/2017")
                .amount(transferDTO.getAmount())
                .currency(transferDTO.getCurrency())
                .isUrgent(false)
                .isInstant(false)
                .feeType("SHA")
                .feeAccountId(transferDTO.getAccountId().toString())
                .taxRelief(TransferFabrickAPIRequestDTO.TaxRelief.builder()
                        .taxReliefId("L449")
                        .isCondoUpgrade(false)
                        .creditorFiscalCode("56258745832")
                        .beneficiaryType("NATURAL_PERSON")
                        .naturalPersonBeneficiary(TransferFabrickAPIRequestDTO.NaturalPersonBeneficiary.builder()
                                .fiscalCode1("MRLFNC81L04A859L")
                                .fiscalCode2(null)
                                .fiscalCode3(null)
                                .fiscalCode4(null)
                                .fiscalCode5(null)
                                .build())
                        .legalPersonBeneficiary(TransferFabrickAPIRequestDTO.LegalPersonBeneficiary.builder()
                                .fiscalCode(null)
                                .legalRepresentativeFiscalCode(null)
                                .build())
                        .build())
                .build();
    }

}
