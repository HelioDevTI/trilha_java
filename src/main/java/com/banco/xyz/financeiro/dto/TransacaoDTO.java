package com.banco.xyz.financeiro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransacaoDTO {

    @NotNull
    private Long idTipo;

    @NotNull
    private Long idConta;

    @NotBlank
    private String descricao;

    @NotNull
    private BigDecimal valor;

}
