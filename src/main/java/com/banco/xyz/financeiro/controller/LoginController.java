package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.constant.PerfisUsuarios;
import com.banco.xyz.financeiro.dto.LoginAtualizarDTO;
import com.banco.xyz.financeiro.dto.LoginDTO;
import com.banco.xyz.financeiro.recod.LoginRecord;
import com.banco.xyz.financeiro.service.LoginService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@SecurityRequirement(name = "bearerAPI")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PreAuthorize(PerfisUsuarios.GERENTE)
    @GetMapping("/{id}")
    public ResponseEntity<LoginRecord> getLogin(@PathVariable("id") Long idLogin){

        return ResponseEntity.ok(loginService.getLogin(idLogin));
    }

    @PreAuthorize(PerfisUsuarios.ADMINISTRADOR)
    @GetMapping()
    public ResponseEntity<Page<LoginRecord>> listaLogins(@PageableDefault(page = 0, size = 10, sort = {"email"})Pageable paginacao){

        return ResponseEntity.ok(loginService.listaLogins(paginacao));
    }

    @PreAuthorize(PerfisUsuarios.GERENTE)
    @PostMapping
    public ResponseEntity<LoginRecord> salvarLogin(@RequestBody @Valid LoginDTO loginDTO){

        return ResponseEntity.status(HttpStatus.CREATED).body(loginService.salvarLogin(loginDTO));

    }

    @PreAuthorize(PerfisUsuarios.GERENTE)
    @PutMapping
    public ResponseEntity<LoginRecord> atualizarLogin(@RequestBody @Valid LoginAtualizarDTO loginAtualizar){

        return ResponseEntity.ok(loginService.atualizarLogin(loginAtualizar));
    }

    @PreAuthorize(PerfisUsuarios.ADMINISTRADOR)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirLogin(@PathVariable("id") Long id){

        return loginService.excluirLogin(id);
    }


}
