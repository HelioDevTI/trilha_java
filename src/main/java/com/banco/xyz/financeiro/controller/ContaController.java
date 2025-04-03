package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.constant.PerfisUsuarios;
import com.banco.xyz.financeiro.dto.ContaAtualizarDTO;
import com.banco.xyz.financeiro.dto.ContaDTO;
import com.banco.xyz.financeiro.recod.ContaRecord;
import com.banco.xyz.financeiro.service.ContaService;
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
@RequestMapping("/conta")
@SecurityRequirement(name = "bearerAPI")
public class ContaController {


    @Autowired
    private ContaService contaService;


    @PreAuthorize(PerfisUsuarios.CORRENTISTA_GERENTE)
    @GetMapping("/{id}")
    public ResponseEntity<ContaRecord> consultaConta(@PathVariable("id") Long id){

        return ResponseEntity.ok(contaService.getConta(id));
    }

    @PreAuthorize(PerfisUsuarios.GERENTE)
    @GetMapping
    public ResponseEntity<Page<ContaRecord>> listaConta(@PageableDefault(page = 0, size = 10, sort = {"numero"})Pageable paginacao){

        return ResponseEntity.ok(contaService.listaConta(paginacao));
    }

    @PreAuthorize(PerfisUsuarios.CORRENTISTA_GERENTE)
    @PostMapping
    public ResponseEntity<ContaRecord> salvarConta(@RequestBody ContaDTO contaDTO){

        return ResponseEntity.status(HttpStatus.CREATED).body(contaService.salvarConta(contaDTO));
    }

    @PreAuthorize(PerfisUsuarios.GERENTE)
    @PutMapping
    public ResponseEntity<ContaRecord> atualizarConta(@RequestBody ContaAtualizarDTO contaAtualizar){

        return ResponseEntity.status(HttpStatus.OK).body(contaService.atualizarConta(contaAtualizar));
    }

    @PreAuthorize(PerfisUsuarios.ADMINISTRADOR)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirConta(@PathVariable("id") Long id){

        return contaService.excluirConta(id);
    }

}
