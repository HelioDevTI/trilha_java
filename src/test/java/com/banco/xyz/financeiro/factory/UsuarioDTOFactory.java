package com.banco.xyz.financeiro.factory;

import com.banco.xyz.financeiro.dto.UsuarioDTO;

public interface UsuarioDTOFactory {

    static UsuarioDTO getUsuarioDTO(){

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf("111111111111");
        usuarioDTO.setNome("Usuario");
        usuarioDTO.setPerfil(1L);

        return usuarioDTO;
    }
}
