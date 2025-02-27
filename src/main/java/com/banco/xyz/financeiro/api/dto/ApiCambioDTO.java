package com.banco.xyz.financeiro.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ApiCambioDTO {

    private boolean success;

    private Long timestamp;

    private String base;

    private LocalDate date;

    @JsonAlias("rates")
    private MoedasDTO moedas;
}
