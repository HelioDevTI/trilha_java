package com.banco.xyz.financeiro.recod;

import java.time.LocalDateTime;

public record LoginRecord(Long idUsuario, String email, String senha,
                          LocalDateTime dataLogin, LocalDateTime dataAtualizacao) {
}
