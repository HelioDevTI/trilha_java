package com.banco.xyz.financeiro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioDTO {

    @NotNull
    private Long perfil;
    @NotBlank
    private String nome;
    @NotBlank
    private String cpf;

}
