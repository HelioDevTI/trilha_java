package com.banco.xyz.financeiro.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UsuarioAtualizarDTO extends UsuarioDTO{

    @NotNull
    private Long id;

}
