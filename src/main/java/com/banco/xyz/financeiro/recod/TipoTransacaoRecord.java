package com.banco.xyz.financeiro.recod;

import com.banco.xyz.financeiro.enumeration.Tipo;

import java.time.LocalDateTime;

public record TipoTransacaoRecord(Long id, String moeda, String descricao, Boolean ativo, Tipo tipo,
                                  LocalDateTime dataCriacao) {
}
