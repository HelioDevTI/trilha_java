package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.api.cambio.ApiExternaCambio;
import com.banco.xyz.financeiro.api.cambio.ApiMockCambio;
import com.banco.xyz.financeiro.business.CalculoTransacoes;
import com.banco.xyz.financeiro.business.CalculoTransacoesMock;
import com.banco.xyz.financeiro.constant.PerfisUsuarios;
import com.banco.xyz.financeiro.recod.CorrentistaRecord;
import com.banco.xyz.financeiro.repository.LoginRepository;
import com.banco.xyz.financeiro.repository.PerfilRepository;
import com.banco.xyz.financeiro.repository.UsuarioRepository;
import com.banco.xyz.financeiro.service.CadastroUsuarioService;
import com.banco.xyz.financeiro.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@WebMvcTest(APICambioController.class)
@ActiveProfiles("test")
public class CadastroUsuarioControllerTest {

    @MockitoBean
    private CadastroUsuarioService cadastroUsuarioService;

    @MockitoBean
    private ApiExternaCambio apiExternaCambio;

    @MockitoBean
    private CalculoTransacoes calculoTransacoes;

    @MockitoBean
    private ApiMockCambio apiMockCambio;

    @MockitoBean
    private CalculoTransacoesMock calculoTransacoesMock;

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

    private static final String URL = "/cadastro/usuario";


/*    @Test
    @WithMockUser(username = "correntista", roles = {"CORRENTISTA", "GERENTE"})
    void cadastroCorrentistaTest() throws Exception {


        CorrentistaRecord correntistaRecord = new CorrentistaRecord("nome1", "111.111.111-11", 1L, 1L,
                1L, new BigDecimal("10.0"), "teste@email.com", "0123456");

        String requestJson = objectMapper.writeValueAsString(correntistaRecord);


        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.post(URL.concat("/correntista"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();


    }*/
}
