package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.constant.PerfisUsuarios;
import com.banco.xyz.financeiro.dto.DadosCorrentistaDTO;
import com.banco.xyz.financeiro.recod.CorrentistaRecord;
import com.banco.xyz.financeiro.service.CadastroUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cadastro/usuario")
public class CadastroUsuarioController {


    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @PreAuthorize(PerfisUsuarios.CORRENTISTA_GERENTE)
    @PostMapping("/correntista")
    public ResponseEntity<DadosCorrentistaDTO> cadastroCorrentista(@RequestBody @Valid CorrentistaRecord correntista){


        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroUsuarioService.cadastroCorrentista(correntista));

    }


}
