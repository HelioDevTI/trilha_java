package com.banco.xyz.financeiro.factory;

import com.banco.xyz.financeiro.dto.UsuarioDTO;

public interface UsuarioDTOFactory {

    static UsuarioDTO getUsuarioDTO(){

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf("272.132.030-02");
        usuarioDTO.setNome("Usuario");
        usuarioDTO.setPerfil(1L);

        return usuarioDTO;
    }
}
