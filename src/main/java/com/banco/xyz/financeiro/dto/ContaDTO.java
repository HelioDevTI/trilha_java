package com.banco.xyz.financeiro.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ContaDTO {

    @NotNull
    private Long idUsuario;

    @NotNull
    private Long numero;

    @NotNull
    private Long digito;

    @NotNull
    private Long agencia;

    @NotNull
    private BigDecimal saldo;

}
