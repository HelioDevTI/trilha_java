package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.dto.UsuarioAtualizarDTO;
import com.banco.xyz.financeiro.dto.UsuarioDTO;
import com.banco.xyz.financeiro.model.Usuario;
import com.banco.xyz.financeiro.recod.UsuarioRecord;
import com.banco.xyz.financeiro.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRecord> consultaUsuario(@PathVariable("id") Long idUsuairo){


        return ResponseEntity.ok(usuarioService.getUsuario(idUsuairo));

    }

    @GetMapping()
    public ResponseEntity<Page<UsuarioRecord>> listaUsuario(@PageableDefault(size = 10, sort = {"nome"})Pageable paginacao){

        return ResponseEntity.ok(usuarioService.listaUsuarios(paginacao));

    }

    @PostMapping()
    public ResponseEntity<String> savarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO){

        return ResponseEntity.ok(usuarioService.savarUsuario(usuarioDTO));

    }

    @PutMapping()
    public ResponseEntity<String> autualizarUsuario(@RequestBody @Valid UsuarioAtualizarDTO usuarioAtualizar){


        return ResponseEntity.ok(usuarioService.atualizarUsuario(usuarioAtualizar));
    }
}
