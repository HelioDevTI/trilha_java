/*
package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.recod.DadosAutenticacao;
import com.banco.xyz.financeiro.repository.LoginRepository;
import com.banco.xyz.financeiro.repository.PerfilRepository;
import com.banco.xyz.financeiro.repository.UsuarioRepository;
import com.banco.xyz.financeiro.security.FiltroSegurancaRequisicao;
import com.banco.xyz.financeiro.security.UsuarioAutenticacao;
import com.banco.xyz.financeiro.service.LoginService;
import com.banco.xyz.financeiro.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@WebMvcTest(AutenticacaoController.class)
@ActiveProfiles("test")
public class AutenticacaoControllerTest {

    @MockBean
    private LoginService loginService;

    @MockBean
    private TokenService tokenService;

    @MockitoBean
    private LoginRepository loginRepository;

    @MockitoBean
    private PerfilRepository perfilRepository;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private UsuarioAutenticacao usuarioAutenticacao;

    @MockitoBean
    private FiltroSegurancaRequisicao filtroSeguranca;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private static final String URL = "/autenticar";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @WithMockUser
    void consultaApiTest() throws Exception {

        DadosAutenticacao dadosAutenticacao = new DadosAutenticacao("teste@email.com", "1234");

        when(loginService.fazerLogin(dadosAutenticacao)).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(usuarioAutenticacao);
        when(tokenService.gerarToken(usuarioAutenticacao)).thenReturn("token123");



        String requestJson = objectMapper.writeValueAsString(dadosAutenticacao);

    */
/*    MvcResult json = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();*//*


    //    String responseJson = json.getResponse().getContentAsString();

     //   Assertions.assertEquals("apiCambioDTO.getBase()", responseJson);

    }
}
*/
