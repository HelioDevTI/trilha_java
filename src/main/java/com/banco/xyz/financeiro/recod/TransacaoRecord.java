package com.banco.xyz.financeiro.recod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoRecord(Long id, Long idTipo, Long idConta, String descricao, BigDecimal valor,
                              BigDecimal valorConvertido, LocalDateTime dataTransacao) {
}
