package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.dto.UsuarioAtualizarDTO;
import com.banco.xyz.financeiro.dto.UsuarioDTO;
import com.banco.xyz.financeiro.factory.TokenFactory;
import com.banco.xyz.financeiro.factory.UsuarioDTOFactory;
import com.banco.xyz.financeiro.recod.UsuarioRecord;
import com.banco.xyz.financeiro.service.UsuarioService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UsuarioControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenFactory tokenFactory;

    @MockitoBean
    private UsuarioService usuarioService;

    private static final String URL = "/usuario";

    private static String tokenCorren;
    private static String tokenGeren;
    private static String tokenAdmin;

    @BeforeAll
    static void setUpAll(
            @Autowired TokenFactory tokenFactory
    ) {
        tokenCorren = tokenFactory.getTokenCorrentista();
        tokenGeren = tokenFactory.getTokenGerente();
        tokenAdmin = tokenFactory.getTokenAdministrador();
    }


    @Test
    void consultaUsuarioTest() throws Exception {

        UsuarioRecord usuarioRecord = new UsuarioRecord(1L, "Usuario", 1L, "1234567", LocalDateTime.now());


        Mockito.when(usuarioService.getUsuario(1L)).thenReturn(usuarioRecord);

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + tokenGeren))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();


        UsuarioRecord usuarioResponse = objectMapper.readValue(stringJson, UsuarioRecord.class);

        Assertions.assertEquals(usuarioResponse.id(), usuarioRecord.id());

    }

    @Test
    void listaUsuarioTest() throws Exception {

        UsuarioRecord usuarioRecord = new UsuarioRecord(1L, "Usuario", 1L, "1234567", LocalDateTime.now());
        UsuarioRecord usuarioRecord2 = new UsuarioRecord(2L, "Usuario2", 2L, "1111111", LocalDateTime.now());

        List<UsuarioRecord> listaUsuario = List.of(usuarioRecord, usuarioRecord2);


        Pageable pageable = PageRequest.of(0, 2);
        Page<UsuarioRecord> page = new PageImpl<>(listaUsuario, pageable, listaUsuario.size());

        Mockito.when(usuarioService.listaUsuarios(Mockito.any())).thenReturn(page);

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("?page=0&size=1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + tokenGeren))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();

        JsonNode rootNode = objectMapper.readTree(stringJson);
        Long totalElementos = rootNode.get("totalElements").asLong();


        Assertions.assertEquals(2, totalElementos);

    }


    @Test
    void salvarUsuarioTest() throws Exception {

        UsuarioDTO usuarioDTO = UsuarioDTOFactory.getUsuarioDTO();
        UsuarioRecord usuarioRecord = new UsuarioRecord(1L ,usuarioDTO.getNome(), usuarioDTO.getPerfil(),
                usuarioDTO.getCpf(), LocalDateTime.now());

        String jsonRequest = objectMapper.writeValueAsString(usuarioDTO);


        Mockito.when(usuarioService.savarUsuario(usuarioDTO)).thenReturn(usuarioRecord);

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + tokenGeren))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();

        UsuarioRecord usuarioResponse = objectMapper.readValue(stringJson, UsuarioRecord.class);

        Assertions.assertEquals(usuarioResponse.id(), usuarioRecord.id());

    }

    @Test
    void atualizarUsuarioTest() throws Exception {

        UsuarioDTO usuarioDTO = UsuarioDTOFactory.getUsuarioDTO();
        UsuarioRecord usuarioRecord = new UsuarioRecord(1L ,usuarioDTO.getNome(), usuarioDTO.getPerfil(),
                usuarioDTO.getCpf(), LocalDateTime.now());

        UsuarioAtualizarDTO usuarioAtualizarDTO = new UsuarioAtualizarDTO();
        usuarioAtualizarDTO.setId(1L);
        usuarioAtualizarDTO.setCpf(usuarioDTO.getCpf());
        usuarioAtualizarDTO.setNome(usuarioDTO.getNome());
        usuarioAtualizarDTO.setPerfil(usuarioDTO.getPerfil());


        String jsonRequest = objectMapper.writeValueAsString(usuarioAtualizarDTO);


        Mockito.when(usuarioService.atualizarUsuario(usuarioAtualizarDTO)).thenReturn(usuarioRecord);

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + tokenCorren))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();

        UsuarioRecord usuarioResponse = objectMapper.readValue(stringJson, UsuarioRecord.class);

        Assertions.assertEquals(usuarioResponse.id(), usuarioRecord.id());

    }

    @Test
    void excluirUsuarioTest() throws Exception {

        String mensagemRetorno = "Usuario excluida com sucesso";


        Mockito.when(usuarioService.excluirUsuario(1L)).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(mensagemRetorno));

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.delete(URL.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + tokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();

        Assertions.assertEquals(mensagemRetorno, stringJson);

    }

}
