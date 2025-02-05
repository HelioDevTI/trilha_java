package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.dto.TransacaoAtualizarDTO;
import com.banco.xyz.financeiro.dto.TransacaoDTO;
import com.banco.xyz.financeiro.recod.TransacaoRecord;
import com.banco.xyz.financeiro.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {


    @Autowired
    private TransacaoService transacaoService;

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoRecord> consultatransacao(@PathVariable("id") Long id){

        return ResponseEntity.ok(transacaoService.getTransacao(id));
    }

    @GetMapping
    public ResponseEntity<Page<TransacaoRecord>> listatransacao(@PageableDefault(size = 10, sort = {"idConta"}) Pageable paginacao){

        return ResponseEntity.ok(transacaoService.listaTransacao(paginacao));
    }

    @PostMapping
    public ResponseEntity<String> salvartransacao(@RequestBody TransacaoDTO transacaoDTO){

        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoService.salvarTransacao(transacaoDTO));
    }

    @PutMapping
    public ResponseEntity<String> atualizartransacao(@RequestBody TransacaoAtualizarDTO transacaoDTO){

        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoService.atualizarTransacao(transacaoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirtransacao(@PathVariable("id") Long id){

        return transacaoService.excluirTransacao(id);
    }

}
