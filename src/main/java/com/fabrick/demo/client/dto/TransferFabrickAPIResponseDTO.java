package com.fabrick.demo.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferFabrickAPIResponseDTO {

    public String status;
    public List<Error> errors;
    public Payload payload;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Root{
        public String status;
        public List<Error> errors;
        public Payload payload;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class Payload{
    }


}
