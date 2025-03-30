package com.banco.xyz.financeiro.factory;

import com.banco.xyz.financeiro.model.Login;
import com.banco.xyz.financeiro.model.Perfil;
import com.banco.xyz.financeiro.model.Usuario;
import com.banco.xyz.financeiro.repository.LoginRepository;
import com.banco.xyz.financeiro.repository.PerfilRepository;
import com.banco.xyz.financeiro.repository.UsuarioRepository;
import com.banco.xyz.financeiro.security.UsuarioAutenticacao;
import com.banco.xyz.financeiro.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class TokenFactory {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private TokenService tokenService;

   public String getTokenCorrentista(){

        log.info("############CRIANDO USUARIO CORRENTISTA############");
        LocalDateTime dataAutal = LocalDateTime.now();

        Perfil perfil = new Perfil(null, "CORRENTISTA", "Teste Unitario", dataAutal);
        Perfil perfilSalvo = perfilRepository.save(perfil);

        Usuario usuario = new Usuario(null, perfilSalvo.getId(), "Usuario Correntista", "111.111.111-11", dataAutal);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        Login login = new Login(null, usuarioSalvo.getId(), "correntista@email.com", "111111", dataAutal, dataAutal);
        Login loginSalvo = loginRepository.save(login);

        return tokenService.gerarToken(new UsuarioAutenticacao(loginSalvo, perfil.getTipo()));
    }

    public String getTokenAdministrador(){

        log.info("############CRIANDO USUARIO ADMINISTRADOR############");

        LocalDateTime dataAutal = LocalDateTime.now();

        Perfil perfil = new Perfil(null, "ADMINISTRADOR", "Teste Administrador", dataAutal);
        Perfil perfilSalvo = perfilRepository.save(perfil);

        Usuario usuario = new Usuario(null, perfilSalvo.getId(), "Usuario Teste", "111.111.111-11", dataAutal);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        Login login = new Login(null, usuarioSalvo.getId(), "administrador@email.com", "222222", dataAutal, dataAutal);
        Login loginSalvo = loginRepository.save(login);

        return tokenService.gerarToken(new UsuarioAutenticacao(loginSalvo, perfil.getTipo()));
    }

    public String getTokenGerente(){

        log.info("############CRIANDO USUARIO GERENTE############");

        LocalDateTime dataAutal = LocalDateTime.now();

        Perfil perfil = new Perfil(null, "GERENTE", "Teste Unitario", dataAutal);
        Perfil perfilSalvo = perfilRepository.save(perfil);

        Usuario usuario = new Usuario(null, perfilSalvo.getId(), "Usuario Gerente", "111.111.111-11", dataAutal);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        Login login = new Login(null, usuarioSalvo.getId(), "gerente@email.com", "333333", dataAutal, dataAutal);
        Login loginSalvo = loginRepository.save(login);

        return tokenService.gerarToken(new UsuarioAutenticacao(loginSalvo, perfil.getTipo()));
    }
}
