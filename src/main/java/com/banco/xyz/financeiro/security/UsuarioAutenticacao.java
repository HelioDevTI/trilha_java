package com.banco.xyz.financeiro.security;

import com.banco.xyz.financeiro.model.Login;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsuarioAutenticacao implements UserDetails {

     private final Login login;


    public  UsuarioAutenticacao(Login login){

        this.login = login;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority("CORRENTISTA"));

    }

    @Override
    public String getPassword() {
        return login.getSenha();
    }

    @Override
    public String getUsername() {
        return login.getEmail();
    }

}
