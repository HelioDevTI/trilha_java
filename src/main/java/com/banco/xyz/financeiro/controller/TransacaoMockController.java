package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.constant.PerfisUsuarios;
import com.banco.xyz.financeiro.dto.TransacaoDTO;
import com.banco.xyz.financeiro.recod.TransacaoRecord;
import com.banco.xyz.financeiro.service.TransacaoMockService;
import com.banco.xyz.financeiro.service.TransacaoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacao/mock")
@SecurityRequirement(name = "bearerAPI")
public class TransacaoMockController {


    @Autowired
    private TransacaoMockService transacaoMockService;

    @Autowired
    private TransacaoService transacaoService;


    @PreAuthorize(PerfisUsuarios.ADMINISTRADOR)
    @PostMapping
    public ResponseEntity<TransacaoRecord> salvarTransacaoMock(@RequestBody TransacaoDTO transacaoDTO){

        TransacaoRecord transacaoRecord = transacaoMockService.salvarTransacao(transacaoDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoRecord);
    }
}
