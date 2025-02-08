package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.dto.TipoTransacaoAtuliDTO;
import com.banco.xyz.financeiro.dto.TipoTransacaoDTO;
import com.banco.xyz.financeiro.recod.TipoTransacaoRecord;
import com.banco.xyz.financeiro.service.TipoTransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tipo/transacao")
public class TipoTransacaoController {


    @Autowired
    private TipoTransacaoService tipoTransacaoService;

    @GetMapping("/{id}")
    public ResponseEntity<TipoTransacaoRecord> consultaTipoTransacao(@PathVariable("id") Long id){

        return ResponseEntity.ok(tipoTransacaoService.getTipoTransacao(id));
    }

    @GetMapping
    public ResponseEntity<Page<TipoTransacaoRecord>> listaTipoTransacao(@PageableDefault(page = 0, size = 10, sort = {"descricao"}) Pageable paginacao){

        return ResponseEntity.ok(tipoTransacaoService.listaTipoTransacao(paginacao));
    }

    @PostMapping
    public ResponseEntity<String> salvarTipoTransacao(@RequestBody TipoTransacaoDTO tipoTransacaoDTO){

        return ResponseEntity.status(HttpStatus.CREATED).body(tipoTransacaoService.salvarTipoTransacao(tipoTransacaoDTO));
    }

    @PutMapping
    public ResponseEntity<String> atualizarTipoTransacao(@RequestBody TipoTransacaoAtuliDTO tipoTransacaoDTO){

        return ResponseEntity.status(HttpStatus.CREATED).body(tipoTransacaoService.atualizarTipoTransacao(tipoTransacaoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirTipoTransacao(@PathVariable("id") Long id){

        return tipoTransacaoService.excluirTipoTransacao(id);
    }

}
