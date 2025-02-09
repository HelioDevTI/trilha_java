package com.banco.xyz.financeiro.service;

import com.banco.xyz.financeiro.model.Login;
import com.banco.xyz.financeiro.model.Perfil;
import com.banco.xyz.financeiro.model.Usuario;
import com.banco.xyz.financeiro.repository.LoginRepository;
import com.banco.xyz.financeiro.repository.PerfilRepository;
import com.banco.xyz.financeiro.repository.UsuarioRepository;
import com.banco.xyz.financeiro.security.UsuarioAutenticacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private LoginRepository loginRepository;


    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Login login = loginRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado no token"));

        Usuario usuarioBanco = usuarioRepository.findById(login.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado no token"));;

        Perfil perfil = perfilRepository.findById(usuarioBanco.getPerfil())
                .orElseThrow(() -> new RuntimeException("Usuario sem perfil"));

        return new UsuarioAutenticacao(login, perfil.getTipo());

    }


}
