package com.banco.xyz.financeiro.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TipoTransacaoAtuliDTO extends TipoTransacaoDTO{

    @NotNull
    private Long id;
}
