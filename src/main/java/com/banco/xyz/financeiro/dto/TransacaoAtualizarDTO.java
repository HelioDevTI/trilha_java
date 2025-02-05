package com.banco.xyz.financeiro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class TransacaoAtualizarDTO{

    @NotNull
    private Long id;

    @NotNull
    private Long idTipo;

    @NotBlank
    private String descricao;

    @NotNull
    private BigDecimal valor;

}
