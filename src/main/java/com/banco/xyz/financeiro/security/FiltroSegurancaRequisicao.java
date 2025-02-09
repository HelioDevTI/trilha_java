package com.banco.xyz.financeiro.security;

import com.banco.xyz.financeiro.model.Login;
import com.banco.xyz.financeiro.model.Perfil;
import com.banco.xyz.financeiro.model.Usuario;
import com.banco.xyz.financeiro.repository.LoginRepository;
import com.banco.xyz.financeiro.repository.PerfilRepository;
import com.banco.xyz.financeiro.repository.UsuarioRepository;
import com.banco.xyz.financeiro.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FiltroSegurancaRequisicao extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = obterToken(request);

        if(token != null){

            String usuario = tokenService.getUserToken(token);

            Login login = loginRepository.findByEmail(usuario)
                    .orElseThrow(() -> new RuntimeException("Usuario nao encontrado no token"));

            Usuario usuarioBanco = usuarioRepository.findById(login.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario nao encontrado no token"));;

            Perfil perfil = perfilRepository.findById(usuarioBanco.getPerfil())
                    .orElseThrow(() -> new RuntimeException("Usuario sem perfil"));

            UsuarioAutenticacao usuarioAutenticacao = new UsuarioAutenticacao(login, perfil.getTipo());

            var autenticacao = new UsernamePasswordAuthenticationToken(usuarioAutenticacao, null, usuarioAutenticacao.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(autenticacao);

        }

        filterChain.doFilter(request, response);
    }

    private String obterToken(HttpServletRequest request){

        var authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Remove "Bearer " do início
        }

        return null;
    }
}
