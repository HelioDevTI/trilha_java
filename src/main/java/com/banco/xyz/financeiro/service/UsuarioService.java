package com.banco.xyz.financeiro.service;

import com.banco.xyz.financeiro.dto.UsuarioAtualizarDTO;
import com.banco.xyz.financeiro.dto.UsuarioDTO;
import com.banco.xyz.financeiro.model.Usuario;
import com.banco.xyz.financeiro.recod.UsuarioRecord;
import com.banco.xyz.financeiro.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioRecord getUsuario(Long idUsuairo){

        return usuarioRepository.findById(idUsuairo)
                .map(usu -> new UsuarioRecord(usu.getId(), usu.getNome(), usu.getPerfil(), usu.getCpf(),
                        usu.getDataCriacao())).orElseThrow(EntityNotFoundException::new);



    }

    public Page<UsuarioRecord> listaUsuarios(Pageable paginacao){

        return usuarioRepository.findAll(paginacao).map(usu -> new UsuarioRecord(usu.getId(), usu.getNome(),
                usu.getPerfil(), usu.getCpf(),  usu.getDataCriacao()));
    }

    public UsuarioRecord savarUsuario(UsuarioDTO usuarioDTO){

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setPerfil(usuarioDTO.getPerfil());
        usuario.setDataCriacao(LocalDateTime.now());

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return new UsuarioRecord(usuarioSalvo);
    }

    public UsuarioRecord atualizarUsuario(UsuarioAtualizarDTO usuarioAtualizar){

        Usuario usuario = usuarioRepository.getReferenceById(usuarioAtualizar.getId());
        usuario.setNome(usuarioAtualizar.getNome());
        usuario.setCpf(usuarioAtualizar.getCpf());
        usuario.setPerfil(usuarioAtualizar.getPerfil());

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);

        return new UsuarioRecord(usuarioAtualizado);
    }

    public ResponseEntity<String> excluirUsuario(Long id){

        Usuario usuario = usuarioRepository.getReferenceById(id);

        if(usuario.getId() == null){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao encontrado");
        }

        usuarioRepository.delete(usuario);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuario excluido com Sucesso!");
    }

}
