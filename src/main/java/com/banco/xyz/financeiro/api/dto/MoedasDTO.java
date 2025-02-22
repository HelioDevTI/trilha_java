package com.banco.xyz.financeiro.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class MoedasDTO {

    @JsonAlias("BRL")
    private Double BRL;

    @JsonAlias("USD")
    private Double USD;

    @JsonAlias("JPY")
    private Double JPY;

    @JsonAlias("ARS")
    private Double ARS;
}
