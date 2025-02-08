package com.banco.xyz.financeiro.recod;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CorrentistaRecord(

        @NotBlank
        String nome,

        @NotBlank
        String cpf,

        @NotNull
        Long numero,

        @NotNull
        Long digito,

        @NotNull
        Long agencia,

        @NotNull
        BigDecimal valorDeposito,

        @NotBlank
        String email,

        @NotBlank
        String senha

   ) {
}
