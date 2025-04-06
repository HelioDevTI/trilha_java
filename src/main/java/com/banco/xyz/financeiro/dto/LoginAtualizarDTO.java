package com.banco.xyz.financeiro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginAtualizarDTO {

    @NotNull
    private Long idLogin;

    @NotBlank
    @Email(message = "Email inválido")
    private String email;

    @NotBlank
    private String senha;

}
