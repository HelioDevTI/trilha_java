package com.banco.xyz.financeiro.recod;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(

        @NotBlank
        String email,

        @NotBlank
        String senha

    ) {
}
