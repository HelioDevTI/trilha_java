package com.banco.xyz.financeiro.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ContaAtualizarDTO extends ContaDTO{

    @NotNull
    private Long id;
}
