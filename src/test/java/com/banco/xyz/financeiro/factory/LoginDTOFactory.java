package com.banco.xyz.financeiro.factory;

import com.banco.xyz.financeiro.dto.LoginDTO;

public interface LoginDTOFactory {

    static LoginDTO getLogin(){

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("teste@teste.com");
        loginDTO.setSenha("123456789");
        loginDTO.setIdUsuario(1L);

        return loginDTO;
    }
}
