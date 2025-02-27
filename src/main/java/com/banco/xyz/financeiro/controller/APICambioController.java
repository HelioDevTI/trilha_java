package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.api.cambio.ApiExternaCambio;
import com.banco.xyz.financeiro.api.cambio.ApiMockCambio;
import com.banco.xyz.financeiro.api.dto.ApiCambioDTO;
import com.banco.xyz.financeiro.business.CalculoTransacoes;
import com.banco.xyz.financeiro.business.CalculoTransacoesMock;
import com.banco.xyz.financeiro.constant.PerfisUsuarios;
import com.banco.xyz.financeiro.enumeration.SiglasMoedas;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/cambio")
@PreAuthorize(PerfisUsuarios.ADMINISTRADOR)
@SecurityRequirement(name = "bearerAPI")
public class APICambioController {

    @Autowired
    private ApiExternaCambio apiExternaCambio;

    @Autowired
    private CalculoTransacoes calculoTransacoes;

    @Autowired
    private ApiMockCambio apiMockCambio;

    @Autowired
    private CalculoTransacoesMock calculoTransacoesMock;


    @GetMapping("/externa")
    public ResponseEntity<ApiCambioDTO> consultaApi(){

        return ResponseEntity.ok().body(apiExternaCambio.chamarAPIExternaCambios());
    }

    @GetMapping("/externa/valor/{valor}/moeda/{moeda}")
    public ResponseEntity<BigDecimal> consultaApiValor(@PathVariable("valor") BigDecimal valor,
                                                       @PathVariable("moeda") SiglasMoedas moeda){

        return ResponseEntity.ok().body(calculoTransacoes.calculoCambio(moeda, valor));
    }

    @GetMapping("/mock")
    public ResponseEntity<ApiCambioDTO> consultaApiMock(){

        return ResponseEntity.ok().body(apiMockCambio.chamarAPIMockCambios());
    }


    @GetMapping("/externa/mock/valor/{valor}/moeda/{moeda}")
    public ResponseEntity<BigDecimal> consultaApiMockValor(@PathVariable("valor") BigDecimal valor,
                                                           @PathVariable("moeda") SiglasMoedas moeda){

        return ResponseEntity.ok().body(calculoTransacoesMock.calculoCambioMock(moeda, valor));
    }
}
