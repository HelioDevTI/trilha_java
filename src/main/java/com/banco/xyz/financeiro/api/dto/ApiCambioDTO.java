package com.banco.xyz.financeiro.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ApiCambioDTO {

    private boolean success;

    private Long timestamp;

    private String base;

    private LocalDate date;

    @JsonAlias("rates")
    private MoedasDTO moedas;
}
