package com.banco.xyz.financeiro.factory;

import com.banco.xyz.financeiro.model.Usuario;

import java.time.LocalDateTime;

public interface UsuarioFactory {

    static Usuario getUsuario(){

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setPerfil(1L);
        usuario.setDataCriacao(LocalDateTime.now());
        usuario.setCpf("111.111.111-11");
        usuario.setNome("Usuario");

        return usuario;
    }
}
