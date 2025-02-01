package com.banco.xyz.financeiro.recod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ContaRecord(Long idUsuario, Long numero, Long digito, Long agencia,
                          BigDecimal saldo, LocalDateTime dataCriacao) {

}
