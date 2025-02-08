package com.banco.xyz.financeiro.recod;

import com.banco.xyz.financeiro.model.Login;

import java.time.LocalDateTime;

public record LoginRecord(Long id, Long idUsuario, String email, String senha,
                          LocalDateTime dataLogin, LocalDateTime dataAtualizacao) {


    public LoginRecord(Login login){

        this(login.getId(), login.getIdUsuario(), login.getEmail(), login.getSenha(),
                login.getDataLogin(), login.getDataAutlizacao());

    }
}
