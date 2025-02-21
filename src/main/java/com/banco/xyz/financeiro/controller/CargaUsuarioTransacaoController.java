package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.constant.PerfisUsuarios;
import com.banco.xyz.financeiro.dto.DadosArquivoCargaUsuario;
import com.banco.xyz.financeiro.service.CargaTransacaoService;
import com.banco.xyz.financeiro.service.CargaUsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/carga")
@PreAuthorize(PerfisUsuarios.GERENTE)
@SecurityRequirement(name = "bearerAPI")
public class CargaUsuarioTransacaoController {

    @Autowired
    private CargaTransacaoService serviceTransacao;

    @Autowired
    private CargaUsuarioService serviceUsuario;

    @PostMapping(value = "/transacao", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> processarTrasacoes(@RequestParam("arquivo")MultipartFile arquivo){

        return ResponseEntity.ok().body(serviceTransacao.processarTrasacoes(arquivo));
    }


    @PostMapping(value = "/usuario", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<DadosArquivoCargaUsuario>> processarUsuario(@RequestParam("arquivo")MultipartFile arquivo){

        return ResponseEntity.ok().body(serviceUsuario.processarUsuarios(arquivo));
    }
}
