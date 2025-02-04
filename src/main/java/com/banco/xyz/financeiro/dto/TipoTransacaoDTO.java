package com.banco.xyz.financeiro.dto;

import com.banco.xyz.financeiro.enumeration.Tipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TipoTransacaoDTO {

    @NotBlank
    private String moeda;

    @NotBlank
    private String descricao;

    @NotNull
    private Boolean ativo;

    @NotNull
    private Tipo tipo;


}
