package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.constant.PerfisUsuarios;
import com.banco.xyz.financeiro.service.TransacaoArquivoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/transacao/arquivo")
@SecurityRequirement(name = "bearerAPI")
public class TransacaoArquivoController {

    @Autowired
    private TransacaoArquivoService service;


    @PreAuthorize(PerfisUsuarios.CORRENTISTA)
    @GetMapping("/download/{idUsuario}")
    public ResponseEntity<byte[]> downloadArquivoTransacoes(@PathVariable("idUsuario") Long idUsuairo){


        String dataAtual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        String nomeArquivo =  "Transacoes_" + dataAtual + ".xlsx";
        nomeArquivo = nomeArquivo.replace(" ", "_").replace("-", "_");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + nomeArquivo);

        return new ResponseEntity<>(service.gerarArquivoTransacao(idUsuairo), headers, HttpStatus.OK);

    }


}
