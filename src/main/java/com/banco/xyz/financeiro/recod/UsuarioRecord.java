package com.banco.xyz.financeiro.recod;

import java.time.LocalDateTime;

public record UsuarioRecord(String nome, Long perfil, String cpf, LocalDateTime dataCriacao) {
}
