package com.banco.xyz.financeiro.recod;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(

        @NotBlank
        @Email(message = "Email inválido")
        String email,

        @NotBlank
        String senha

    ) {
}
