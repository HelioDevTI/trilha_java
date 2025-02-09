package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.recod.DadosAutenticacao;
import com.banco.xyz.financeiro.security.UsuarioAutenticacao;
import com.banco.xyz.financeiro.service.LoginService;
import com.banco.xyz.financeiro.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autenticar")
public class AutenticacaoController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<String> fazerLogin(@RequestBody @Valid DadosAutenticacao dadosAutenticacao){

        Authentication autenticacao = loginService.fazerLogin(dadosAutenticacao);

        var token =  tokenService.gerarToken((UsuarioAutenticacao) autenticacao.getPrincipal());
        return ResponseEntity.ok().body(token);
    }
}
