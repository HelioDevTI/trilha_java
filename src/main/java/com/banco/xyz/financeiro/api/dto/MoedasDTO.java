package com.banco.xyz.financeiro.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MoedasDTO {

    @JsonAlias("BRL")
    private BigDecimal real;

    @JsonAlias("USD")
    private BigDecimal dolar;

    @JsonAlias("JPY")
    private BigDecimal iene;

    @JsonAlias("ARS")
    private BigDecimal pesoArgentino;
}
