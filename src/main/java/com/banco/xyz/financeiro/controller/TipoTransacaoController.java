package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.constant.PerfisUsuarios;
import com.banco.xyz.financeiro.dto.TipoTransacaoAtuliDTO;
import com.banco.xyz.financeiro.dto.TipoTransacaoDTO;
import com.banco.xyz.financeiro.recod.TipoTransacaoRecord;
import com.banco.xyz.financeiro.service.TipoTransacaoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tipo/transacao")
@SecurityRequirement(name = "bearerAPI")
public class TipoTransacaoController {


    @Autowired
    private TipoTransacaoService tipoTransacaoService;

    @PreAuthorize(PerfisUsuarios.ADMINISTRADOR)
    @GetMapping("/{id}")
    public ResponseEntity<TipoTransacaoRecord> consultaTipoTransacao(@PathVariable("id") Long id){

        return ResponseEntity.ok(tipoTransacaoService.getTipoTransacao(id));
    }

    @PreAuthorize(PerfisUsuarios.ADMINISTRADOR)
    @GetMapping
    public ResponseEntity<Page<TipoTransacaoRecord>> listaTipoTransacao(@PageableDefault(page = 0, size = 10, sort = {"descricao"}) Pageable paginacao){

        return ResponseEntity.ok(tipoTransacaoService.listaTipoTransacao(paginacao));
    }

    @PreAuthorize(PerfisUsuarios.ADMINISTRADOR)
    @PostMapping
    public ResponseEntity<String> salvarTipoTransacao(@RequestBody TipoTransacaoDTO tipoTransacaoDTO){

        return ResponseEntity.status(HttpStatus.CREATED).body(tipoTransacaoService.salvarTipoTransacao(tipoTransacaoDTO));
    }

    @PreAuthorize(PerfisUsuarios.ADMINISTRADOR)
    @PutMapping
    public ResponseEntity<String> atualizarTipoTransacao(@RequestBody TipoTransacaoAtuliDTO tipoTransacaoDTO){

        return ResponseEntity.status(HttpStatus.CREATED).body(tipoTransacaoService.atualizarTipoTransacao(tipoTransacaoDTO));
    }

    @PreAuthorize(PerfisUsuarios.ADMINISTRADOR)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirTipoTransacao(@PathVariable("id") Long id){

        return tipoTransacaoService.excluirTipoTransacao(id);
    }

}
