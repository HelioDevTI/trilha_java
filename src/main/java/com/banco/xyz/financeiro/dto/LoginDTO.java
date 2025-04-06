package com.banco.xyz.financeiro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDTO {

    @NotNull
    private Long idUsuario;

    @NotBlank
    @Email(message = "Email inválido")
    private String email;

    @NotBlank
    private String senha;

}
