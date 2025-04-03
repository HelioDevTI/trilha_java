package com.banco.xyz.financeiro.service;

import com.banco.xyz.financeiro.factory.LoginFactory;
import com.banco.xyz.financeiro.factory.PerfilFactory;
import com.banco.xyz.financeiro.factory.UsuarioFactory;
import com.banco.xyz.financeiro.model.Login;
import com.banco.xyz.financeiro.model.Perfil;
import com.banco.xyz.financeiro.model.Usuario;
import com.banco.xyz.financeiro.repository.LoginRepository;
import com.banco.xyz.financeiro.repository.PerfilRepository;
import com.banco.xyz.financeiro.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class AutenticacaoServiceTest {

    @MockitoBean
    private LoginRepository loginRepository;


    @MockitoBean
    private PerfilRepository perfilRepository;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @Mock
    private UserDetailsService userDetailsService;

    @Autowired
    private AutenticacaoService autenticacaoService;


    @Test
    void loadUserByUsernameTest(){


        String email = "teste@email.com";

        Login login = LoginFactory.getLogin();
        Optional<Login> loginOptional = Optional.of(login);

        Usuario usuario = UsuarioFactory.getUsuario();
        Optional<Usuario> usuarioOptional = Optional.of(usuario);

        Perfil perfil = PerfilFactory.getPerfil();
        Optional<Perfil> perfilOptional = Optional.of(perfil);

        Mockito.when(loginRepository.findByEmail(email)).thenReturn(loginOptional);
        Mockito.when(usuarioRepository.findById(login.getIdUsuario())).thenReturn(usuarioOptional);
        Mockito.when(perfilRepository.findById(usuario.getPerfil())).thenReturn(perfilOptional);

        UserDetails userDetails = autenticacaoService.loadUserByUsername(email);


        Assertions.assertEquals(login.getSenha(), userDetails.getPassword());
    }


}
