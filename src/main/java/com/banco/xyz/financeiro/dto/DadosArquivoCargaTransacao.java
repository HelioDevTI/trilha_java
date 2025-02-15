package com.banco.xyz.financeiro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Data
public class DadosArquivoCargaTransacao {

    private String nome;
    private String email;
    private String tipoTransacao;
    private String descricao;
    private String moeda;
    private BigDecimal valor;
    private LocalDate data;
    private LocalTime hora;

}
