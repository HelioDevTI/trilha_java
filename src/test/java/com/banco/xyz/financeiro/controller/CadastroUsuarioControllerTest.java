package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.api.cambio.ApiExternaCambio;
import com.banco.xyz.financeiro.api.cambio.ApiMockCambio;
import com.banco.xyz.financeiro.business.CalculoTransacoes;
import com.banco.xyz.financeiro.business.CalculoTransacoesMock;
import com.banco.xyz.financeiro.constant.PerfisUsuarios;
import com.banco.xyz.financeiro.dto.DadosCorrentistaDTO;
import com.banco.xyz.financeiro.factory.TokenFactory;
import com.banco.xyz.financeiro.recod.CorrentistaRecord;
import com.banco.xyz.financeiro.repository.LoginRepository;
import com.banco.xyz.financeiro.repository.PerfilRepository;
import com.banco.xyz.financeiro.repository.UsuarioRepository;
import com.banco.xyz.financeiro.service.CadastroUsuarioService;
import com.banco.xyz.financeiro.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
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

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CadastroUsuarioControllerTest {

    @MockitoBean
    private ApiExternaCambio apiExternaCambio;

 /*   @MockitoBean
    private CalculoTransacoes calculoTransacoes;

    @MockitoBean
    private ApiMockCambio apiMockCambio;

    @MockitoBean
    private CalculoTransacoesMock calculoTransacoesMock;
*/
    @MockitoBean
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenFactory tokenFactory;

 /*   @Autowired
    private TokenService tokenService;

    @MockitoBean
    private LoginRepository loginRepository;

    @MockitoBean
    private PerfilRepository perfilRepository;

    @MockitoBean
    private UsuarioRepository usuarioRepository;*/

    private static final String URL = "/cadastro/usuario";


    private static String tokenCorren;

    @BeforeAll
    static void setUpAll(
            @Autowired TokenFactory tokenFactory
    ) {
        tokenCorren = tokenFactory.getTokenCorrentista();

    }


    @Test
    void cadastroCorrentistaTest() throws Exception {


        CorrentistaRecord correntistaRecord = new CorrentistaRecord("nome1", "111.111.111-11", 1L, 1L,
                1L, new BigDecimal("10.0"), "teste@email.com", "0123456");

        String requestJson = objectMapper.writeValueAsString(correntistaRecord);

        Mockito.when(cadastroUsuarioService.cadastroCorrentista(correntistaRecord))
                .thenReturn(new DadosCorrentistaDTO(null, null, null));


        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.post(URL.concat("/correntista"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                .header("Authorization", "Bearer " + tokenCorren))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();


    }
}
