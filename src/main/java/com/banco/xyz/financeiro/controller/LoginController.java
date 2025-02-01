package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.dto.LoginAtualizarDTO;
import com.banco.xyz.financeiro.dto.LoginDTO;
import com.banco.xyz.financeiro.recod.LoginRecord;
import com.banco.xyz.financeiro.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/{id}")
    public ResponseEntity<LoginRecord> getLogin(@PathVariable("id") Long idLogin){

        return ResponseEntity.ok(loginService.getLogin(idLogin));
    }

    @GetMapping()
    public ResponseEntity<Page<LoginRecord>> listaLogins(@PageableDefault(size = 10, sort = {"email"})Pageable paginacao){

        return ResponseEntity.ok(loginService.listaLogins(paginacao));
    }

    @PostMapping
    public ResponseEntity<String> salvarLogin(@RequestBody @Valid LoginDTO loginDTO){

        return ResponseEntity.status(HttpStatus.CREATED).body(loginService.salvarLogin(loginDTO));

    }

    @PutMapping
    public ResponseEntity<String> atualizarLogin(@RequestBody @Valid LoginAtualizarDTO loginAtualizar){

        return ResponseEntity.ok(loginService.atualizarLogin(loginAtualizar));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirLogin(@PathVariable("id") Long id){

        return loginService.excluirLogin(id);
    }


}
