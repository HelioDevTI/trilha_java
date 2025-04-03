package com.banco.xyz.financeiro.factory;

import com.banco.xyz.financeiro.model.Perfil;

import java.time.LocalDateTime;

public interface PerfilFactory {

    static Perfil getPerfil(){

        Perfil perfil = new Perfil();
        perfil.setId(1L);
        perfil.setTipo("ADM");
        perfil.setDescricao("Administrador");
        perfil.setDataCriacao(LocalDateTime.now());

        return perfil;
    }
}
