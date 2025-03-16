package com.banco.xyz.financeiro.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.banco.xyz.financeiro.Exception.TokenInvalidoException;
import com.banco.xyz.financeiro.security.UsuarioAutenticacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private final static String APLICACAO = "API Financeira";

    public String gerarToken(UsuarioAutenticacao usuario){

        var algoritimo = Algorithm.HMAC256(secret);

        log.info("Data Criacao Token = {}", dataCiracao());
        log.info("Data Expiracao Token = {}", dataExpiracao());

        try{
            return JWT.create()
                    .withIssuer(APLICACAO)
                    .withSubject(usuario.getUsername())
                    .withClaim("role", usuario.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse(null))
                    .withIssuedAt(dataCiracao())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritimo);
        }catch (JWTCreationException exception){

            throw new JWTCreationException("Erro ao gerar token mensagem", exception);
        }


    }

    private Instant dataCiracao() {
        return Instant.now();
    }


    private Instant dataExpiracao() {
        return Instant.now().plus(3, ChronoUnit.HOURS);
    }

    public String getUserToken(String token){

        var aloritimo = Algorithm.HMAC256(secret);

        log.info("Sergredo = {}", secret);

        try {
            return JWT.require(aloritimo)
                    .withIssuer(APLICACAO)
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException e){

            log.error("Erro no JWT msg = [{}] e trace = [{}]", e.getMessage(), e.getStackTrace());
            throw new TokenInvalidoException("Token JWT inválido ou expirado");
        }

    }
}
