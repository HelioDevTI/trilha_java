package com.banco.xyz.financeiro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginDTO {

    @NotNull
    private Long idUsuario;

    @NotBlank
    private String email;

    @NotBlank
    private String senha;

}
