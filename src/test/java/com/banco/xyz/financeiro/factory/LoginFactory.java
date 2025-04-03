package com.banco.xyz.financeiro.factory;

import com.banco.xyz.financeiro.model.Login;

import java.time.LocalDateTime;

public interface LoginFactory {

    static Login getLogin(){

        Login login = new Login();
        login.setId(1L);
        login.setSenha("123456789");
        login.setDataAutlizacao(LocalDateTime.now());
        login.setDataLogin(LocalDateTime.now());
        login.setEmail("teste@teste.com");
        login.setIdUsuario(1L);


        return login;
    }


}
