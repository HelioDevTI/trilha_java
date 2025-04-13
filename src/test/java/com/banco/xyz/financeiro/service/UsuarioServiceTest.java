package com.banco.xyz.financeiro.service;

import com.banco.xyz.financeiro.dto.UsuarioAtualizarDTO;
import com.banco.xyz.financeiro.dto.UsuarioDTO;
import com.banco.xyz.financeiro.factory.UsuarioDTOFactory;
import com.banco.xyz.financeiro.factory.UsuarioFactory;
import com.banco.xyz.financeiro.model.Usuario;
import com.banco.xyz.financeiro.recod.UsuarioRecord;
import com.banco.xyz.financeiro.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {


    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;


    @Test
    void getUsuarioTest(){

        Long idUsuario = 1L;

        Usuario usuario = UsuarioFactory.getUsuario();
        Optional<Usuario> loginOptional = Optional.of(usuario);

        Mockito.when(usuarioRepository.findById(idUsuario)).thenReturn(loginOptional);

        UsuarioRecord usuarioRecord = usuarioService.getUsuario(idUsuario);

        Assertions.assertEquals(usuario.getId(), usuarioRecord.id());
    }


    @Test
    void listaUsuariosTest(){

        Usuario usuario = UsuarioFactory.getUsuario();
        Usuario usuario2 = UsuarioFactory.getUsuario();
        usuario2.setNome("Usario 2");
        usuario2.setId(2L);
        usuario2.setCpf("222.222.222-22");


        List<Usuario> listaUsuario = List.of(usuario, usuario2);

        Pageable pageable = PageRequest.of(0, 2);
        Page<Usuario> page = new PageImpl<>(listaUsuario, pageable, listaUsuario.size());

        Mockito.when(usuarioRepository.findAll(pageable)).thenReturn(page);

        Page<UsuarioRecord> paginaUsuario = usuarioService.listaUsuarios(pageable);

        Assertions.assertEquals(2, paginaUsuario.getTotalElements());

    }

    @Test
    void savarUsuarioTest(){

        UsuarioDTO usuarioDTO = UsuarioDTOFactory.getUsuarioDTO();
        Usuario usuario = UsuarioFactory.getUsuario();


        Mockito.when(usuarioRepository.save(Mockito.any())).thenReturn(usuario);

        UsuarioRecord usuarioRecordSalvo = usuarioService.savarUsuario(usuarioDTO);

        Assertions.assertEquals(usuario.getCpf(), usuarioRecordSalvo.cpf());
    }

    @Test
    void atualizarUsuarioTest(){

        UsuarioAtualizarDTO usuarioDTO = new UsuarioAtualizarDTO();
        usuarioDTO.setPerfil(1L);
        usuarioDTO.setNome("Novo Usuario");
        usuarioDTO.setId(1L);
        usuarioDTO.setCpf("999.999.999-99");

        Usuario usuario = UsuarioFactory.getUsuario();
        usuario.setCpf(usuarioDTO.getCpf());


        Mockito.when(usuarioRepository.save(Mockito.any())).thenReturn(usuario);
        Mockito.when(usuarioRepository.getReferenceById(usuarioDTO.getId())).thenReturn(usuario);

        UsuarioRecord usuarioRecordSalvo = usuarioService.atualizarUsuario(usuarioDTO);

        Assertions.assertEquals(usuarioDTO.getCpf(), usuarioRecordSalvo.cpf());
    }

    @Test
    void excluirUsuarioNaoEncontradoTest(){

        String msg = "Usuario nao encontrado";

        Mockito.when(usuarioRepository.getReferenceById(1L)).thenReturn(new Usuario());

        ResponseEntity<String> response = usuarioService.excluirUsuario(1L);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals(msg, response.getBody());
    }

    @Test
    void excluirUsuarioTest(){

        String msg = "Usuario excluido com Sucesso!";

        Mockito.when(usuarioRepository.getReferenceById(1L)).thenReturn(UsuarioFactory.getUsuario());

        ResponseEntity<String> response = usuarioService.excluirUsuario(1L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertEquals(msg, response.getBody());
    }


}
