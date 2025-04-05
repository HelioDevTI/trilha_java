package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.constant.PerfisUsuarios;
import com.banco.xyz.financeiro.dto.UsuarioAtualizarDTO;
import com.banco.xyz.financeiro.dto.UsuarioDTO;
import com.banco.xyz.financeiro.recod.UsuarioRecord;
import com.banco.xyz.financeiro.service.UsuarioService;
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
@RequestMapping("/usuario")
@SecurityRequirement(name = "bearerAPI")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @PreAuthorize(PerfisUsuarios.GERENTE)
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRecord> consultaUsuario(@PathVariable("id") Long idUsuairo){


        return ResponseEntity.ok(usuarioService.getUsuario(idUsuairo));

    }

    @PreAuthorize(PerfisUsuarios.GERENTE)
    @GetMapping()
    public ResponseEntity<Page<UsuarioRecord>> listaUsuario(@PageableDefault(page = 0, size = 10, sort = {"nome"})Pageable paginacao){

        return ResponseEntity.ok(usuarioService.listaUsuarios(paginacao));

    }

    @PreAuthorize(PerfisUsuarios.CORRENTISTA_GERENTE)
    @PostMapping()
    public ResponseEntity<UsuarioRecord> savarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO){

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.savarUsuario(usuarioDTO));

    }

    @PreAuthorize(PerfisUsuarios.CORRENTISTA_GERENTE)
    @PutMapping()
    public ResponseEntity<UsuarioRecord> autualizarUsuario(@RequestBody @Valid UsuarioAtualizarDTO usuarioAtualizar){


        return ResponseEntity.ok(usuarioService.atualizarUsuario(usuarioAtualizar));
    }

    @PreAuthorize(PerfisUsuarios.ADMINISTRADOR)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirUsuario(@PathVariable("id") Long id){

        return usuarioService.excluirUsuario(id);
    }
}
