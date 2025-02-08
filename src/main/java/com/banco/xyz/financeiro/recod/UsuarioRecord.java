package com.banco.xyz.financeiro.recod;

import com.banco.xyz.financeiro.model.Usuario;

import java.time.LocalDateTime;

public record UsuarioRecord(Long id, String nome, Long perfil, String cpf, LocalDateTime dataCriacao) {




    public UsuarioRecord( Usuario usuario) {

        this(usuario.getId(), usuario.getNome(), usuario.getPerfil(), usuario.getCpf(), usuario.getDataCriacao());
    }
}
