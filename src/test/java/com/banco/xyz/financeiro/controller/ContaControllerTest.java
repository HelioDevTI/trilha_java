package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.constant.PerfisUsuarios;
import com.banco.xyz.financeiro.dto.ContaDTO;
import com.banco.xyz.financeiro.factory.ContaDTOFactory;
import com.banco.xyz.financeiro.recod.ContaRecord;
import com.banco.xyz.financeiro.repository.LoginRepository;
import com.banco.xyz.financeiro.repository.PerfilRepository;
import com.banco.xyz.financeiro.repository.UsuarioRepository;
import com.banco.xyz.financeiro.service.ContaService;
import com.banco.xyz.financeiro.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@WebMvcTest(ContaController.class)
//@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
//@Import(SecurityConfig.class)
public class ContaControllerTest {

    @MockitoBean
    private ContaService contaService;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private LoginRepository loginRepository;

    @MockitoBean
    private PerfilRepository perfilRepository;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String URL = "/conta";

    @Test
    @WithMockUser(username = "admin", roles = {PerfisUsuarios.CORRENTISTA_GERENTE})
    void consultaContaTest() throws Exception {

        ContaRecord contaRequest = new ContaRecord(1L, 1L, 1L, 1L, 1L,
                new BigDecimal("300.55"), LocalDateTime.now());

        Mockito.when(contaService.getConta(1L)).thenReturn(contaRequest);

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();


        ContaRecord contaResponse = objectMapper.readValue(stringJson, ContaRecord.class);

        Assertions.assertEquals(contaResponse.numero(), contaRequest.numero());

    }

  /*  @Test
    //@WithMockUser(username = "admin", roles = {PerfisUsuarios.CORRENTISTA_GERENTE})
    void salvarContaTest() throws Exception {

        String mensagemRetorno = "Conta criada com sucesso";

        ContaDTO contaDTO = ContaDTOFactory.getContaDTO();

        String jsonRequest = objectMapper.writeValueAsString(contaDTO);


        Mockito.when(contaService.salvarConta(contaDTO)).thenReturn(mensagemRetorno);

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();

        Assertions.assertEquals(mensagemRetorno, stringJson);

    }

    @Test
    public void testePostTest() throws Exception {
        String corpo = "teste";
        mockMvc.perform(MockMvcRequestBuilders.post(URL.concat("/teste"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"" + corpo + "\"")
                        .with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }*/
}
