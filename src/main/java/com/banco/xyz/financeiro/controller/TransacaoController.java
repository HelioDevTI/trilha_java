package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.constant.PerfisUsuarios;
import com.banco.xyz.financeiro.dto.TransacaoAtualizarDTO;
import com.banco.xyz.financeiro.dto.TransacaoDTO;
import com.banco.xyz.financeiro.enumeration.Mes;
import com.banco.xyz.financeiro.proxy.DadosMetricasTransacaoProxy;
import com.banco.xyz.financeiro.proxy.DadosTransacaoProxy;
import com.banco.xyz.financeiro.recod.TransacaoRecord;
import com.banco.xyz.financeiro.service.TransacaoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@RestController
@RequestMapping("/transacao")
@SecurityRequirement(name = "bearerAPI")
public class TransacaoController {


    @Autowired
    private TransacaoService transacaoService;

    @PreAuthorize(PerfisUsuarios.GERENTE)
    @GetMapping("/{id}")
    public ResponseEntity<TransacaoRecord> consultaTransacao(@PathVariable("id") Long id){

        return ResponseEntity.ok(transacaoService.getTransacao(id));
    }

    @PreAuthorize(PerfisUsuarios.CORRENTISTA_GERENTE)
    @GetMapping
    public ResponseEntity<Page<TransacaoRecord>> listaTransacao(@PageableDefault(page = 0, size = 10, sort = {"idConta"}) Pageable paginacao){

        return ResponseEntity.ok(transacaoService.listaTransacao(paginacao));
    }

    @PreAuthorize(PerfisUsuarios.CORRENTISTA)
    @GetMapping("/consulta")
    public ResponseEntity<List<DadosTransacaoProxy>> consultaTransacao(
           @RequestParam("idUsuario") Long idUsuario,
           @RequestParam(name = "dataIncio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd/MM/yyyy") LocalDate dataInicio,
           @RequestParam(name = "dataFim", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd/MM/yyyy") LocalDate dataFim,
           @RequestParam(name = "tipoTransacao", required = false) String tipoTransacao,
           @RequestParam(name = "descCompra", required = false) String descCompra){

        return ResponseEntity.ok().body(transacaoService.consultaTransacao(idUsuario, dataInicio, dataFim, tipoTransacao, descCompra));
    }

    @PreAuthorize(PerfisUsuarios.CORRENTISTA_GERENTE)
    @GetMapping("/consulta/metricas/conta/{idConta}/mes/{mes}/ano/{ano}")
    public ResponseEntity<List<DadosMetricasTransacaoProxy>> consultaMetricasTransacao(@PathVariable("idConta") Long idConta,
                                                                                       @PathVariable("mes") Mes mes,
                                                                                       @PathVariable("ano") Long ano){

        return ResponseEntity.ok().body(transacaoService.consultaMetricasTransacoes(idConta, mes, ano));
    }


    @PreAuthorize(PerfisUsuarios.CORRENTISTA)
    @PostMapping
    public ResponseEntity<TransacaoRecord> salvarTransacao(@RequestBody TransacaoDTO transacaoDTO){

        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoService.salvarTransacao(transacaoDTO));
    }

    @PreAuthorize(PerfisUsuarios.GERENTE)
    @PutMapping
    public ResponseEntity<TransacaoRecord> atualizarTransacao(@RequestBody TransacaoAtualizarDTO transacaoDTO){

        return ResponseEntity.status(HttpStatus.OK).body(transacaoService.atualizarTransacao(transacaoDTO));
    }

    @PreAuthorize(PerfisUsuarios.GERENTE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirTransacao(@PathVariable("id") Long id){

        return transacaoService.excluirTransacao(id);
    }

}
