package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.api.cambio.ApiExternaCambio;
import com.banco.xyz.financeiro.api.cambio.ApiMockCambio;
import com.banco.xyz.financeiro.api.dto.ApiCambioDTO;
import com.banco.xyz.financeiro.business.CalculoTransacoes;
import com.banco.xyz.financeiro.business.CalculoTransacoesMock;
import com.banco.xyz.financeiro.constant.PerfisUsuarios;
import com.banco.xyz.financeiro.enumeration.SiglasMoedas;
import com.banco.xyz.financeiro.factory.ApiCambioDTOFactory;
import com.banco.xyz.financeiro.repository.LoginRepository;
import com.banco.xyz.financeiro.repository.PerfilRepository;
import com.banco.xyz.financeiro.repository.UsuarioRepository;
import com.banco.xyz.financeiro.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
public class APICambioControllerTest {


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

    private ApiCambioDTO apiCambioDTO;

    private static final String URL = "/api/cambio";

    @BeforeEach
    void setUp(){

        apiCambioDTO = ApiCambioDTOFactory.getApiCambioDTO();
        Mockito.when(apiExternaCambio.chamarAPIExternaCambios()).thenReturn(apiCambioDTO);
        Mockito.when(apiMockCambio.chamarAPIMockCambios()).thenReturn(apiCambioDTO);
        Mockito.when(calculoTransacoes.calculoCambio(SiglasMoedas.REAL, new BigDecimal("10.00")))
                .thenReturn(new BigDecimal("20.00"));
        Mockito.when(calculoTransacoesMock.calculoCambioMock(SiglasMoedas.REAL, new BigDecimal("10.00")))
                .thenReturn(new BigDecimal("20.00"));

    }


    @Test
    @WithMockUser(username = "admin", roles = {PerfisUsuarios.ADMINISTRADOR})
    void consultaApiTest() throws Exception {

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/externa"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();


        ApiCambioDTO response = objectMapper.readValue(stringJson, ApiCambioDTO.class);

        Assertions.assertEquals(apiCambioDTO.getBase(), response.getBase());

    }

    @Test
    @WithMockUser(username = "admin", roles = {PerfisUsuarios.ADMINISTRADOR})
    void consultaApiValorTest() throws Exception {

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/externa/valor/10.00/moeda/REAL"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();

        Assertions.assertEquals("20.00", stringJson);
    }


    @Test
    @WithMockUser(username = "admin", roles = {PerfisUsuarios.ADMINISTRADOR})
    void consultaApiMockTest() throws Exception {

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/mock"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();


        ApiCambioDTO response = objectMapper.readValue(stringJson, ApiCambioDTO.class);

        Assertions.assertEquals(apiCambioDTO.getBase(), response.getBase());

    }

    @Test
    @WithMockUser(username = "admin", roles = {PerfisUsuarios.ADMINISTRADOR})
    void consultaApiMockValorTest() throws Exception {

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/externa/mock/valor/10.00/moeda/REAL"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();

        Assertions.assertEquals("20.00", stringJson);
    }
}
