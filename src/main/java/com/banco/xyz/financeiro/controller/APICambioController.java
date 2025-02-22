package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.api.cambio.ApiExternaCambio;
import com.banco.xyz.financeiro.api.dto.ApiCambioDTO;
import com.banco.xyz.financeiro.constant.PerfisUsuarios;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cambio")
@PreAuthorize(PerfisUsuarios.ADMINISTRADOR)
@SecurityRequirement(name = "bearerAPI")
public class APICambioController {

    @Autowired
    private ApiExternaCambio apiExternaCambio;


    @GetMapping("/externa")
    public ResponseEntity<ApiCambioDTO> consultaApi(){

        return ResponseEntity.ok().body(apiExternaCambio.chamarAPIExternaCambios());
    }
}
