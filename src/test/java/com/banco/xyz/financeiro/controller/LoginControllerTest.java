package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.dto.LoginAtualizarDTO;
import com.banco.xyz.financeiro.dto.LoginDTO;
import com.banco.xyz.financeiro.factory.LoginDTOFactory;
import com.banco.xyz.financeiro.factory.TokenFactory;
import com.banco.xyz.financeiro.recod.LoginRecord;
import com.banco.xyz.financeiro.service.LoginService;
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
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenFactory tokenFactory;

    @MockitoBean
    private LoginService loginService;

    private static final String URL = "/login";

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
    void getLoginTest() throws Exception {

        LoginRecord loginRequest = new LoginRecord(1L, 1L, "teste@teste.com", "1234",
                LocalDateTime.now(), LocalDateTime.now());

        Mockito.when(loginService.getLogin(1L)).thenReturn(loginRequest);

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + tokenGeren))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();


        LoginRecord loginResponse = objectMapper.readValue(stringJson, LoginRecord.class);

        Assertions.assertEquals(loginRequest.id(), loginResponse.id());

    }


    @Test
    void listaLoginsTest() throws Exception {

        LoginRecord loginRequest = new LoginRecord(1L, 1L, "teste@teste.com", "1234",
                LocalDateTime.now(), LocalDateTime.now());

        LoginRecord loginRequest2 = new LoginRecord(2L, 2L, "teste2@teste2.com", "12345768",
                LocalDateTime.now(), LocalDateTime.now());

        List<LoginRecord> listaLogin = List.of(loginRequest, loginRequest2);

        Pageable pageable = PageRequest.of(0, 2);
        Page<LoginRecord> page = new PageImpl<>(listaLogin, pageable, listaLogin.size());

        Mockito.when(loginService.listaLogins(Mockito.any())).thenReturn(page);

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("?page=0&size=1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + tokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();

        JsonNode rootNode = objectMapper.readTree(stringJson);
        Long totalElementos = rootNode.get("totalElements").asLong();


        Assertions.assertEquals(2, totalElementos);

    }

    @Test
    void salvarLoginTest() throws Exception {

        String mensagemRetorno = "Login criado com sucesso";

        LoginDTO loginDTO = LoginDTOFactory.getLogin();
       LoginRecord loginRecord = new LoginRecord(1L, loginDTO.getIdUsuario(),
               loginDTO.getEmail(), loginDTO.getSenha(), LocalDateTime.now(), LocalDateTime.now());

        String jsonRequest = objectMapper.writeValueAsString(loginDTO);


        Mockito.when(loginService.salvarLogin(loginDTO)).thenReturn(loginRecord);

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + tokenGeren))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();

        LoginRecord loginResponse = objectMapper.readValue(stringJson, LoginRecord.class);

        Assertions.assertEquals(loginRecord.id(), loginResponse.id());

    }

    @Test
    void atualizarLoginTest() throws Exception {

        LoginAtualizarDTO loginAtualizar = new LoginAtualizarDTO();
        loginAtualizar.setIdLogin(1L);
        loginAtualizar.setEmail("teste2@teste.com");
        loginAtualizar.setSenha("999999");

        LoginRecord loginRecord = new LoginRecord(loginAtualizar.getIdLogin(), 1L,
                loginAtualizar.getEmail(), loginAtualizar.getSenha(), LocalDateTime.now(), LocalDateTime.now());

        String jsonRequest = objectMapper.writeValueAsString(loginAtualizar);


        Mockito.when(loginService.atualizarLogin(loginAtualizar)).thenReturn(loginRecord);

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + tokenGeren))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();

        LoginRecord loginResponse = objectMapper.readValue(stringJson, LoginRecord.class);

        Assertions.assertEquals(loginRecord.id(), loginResponse.id());

    }

    @Test
    void excluirContaTest() throws Exception {

        String mensagemRetorno = "Login Excluido com sucesso";


        Mockito.when(loginService.excluirLogin(1L)).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).body(mensagemRetorno));

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.delete(URL.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + tokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();

        Assertions.assertEquals(mensagemRetorno, stringJson);

    }
}
