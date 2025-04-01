package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.dto.TransacaoDTO;
import com.banco.xyz.financeiro.factory.TokenFactory;
import com.banco.xyz.financeiro.factory.TransacaoDTOFactory;
import com.banco.xyz.financeiro.recod.TransacaoRecord;
import com.banco.xyz.financeiro.service.TransacaoMockService;
import com.banco.xyz.financeiro.service.TransacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TransacaoMockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenFactory tokenFactory;

    @MockitoBean
    private TransacaoMockService transacaoMockService;

    @MockitoBean
    private TransacaoService transacaoService;

    private static final String URL = "/transacao/mock";


    private static String tokenAdmin;

    @BeforeAll
    static void setUpAll(
            @Autowired TokenFactory tokenFactory
    ) {
        tokenAdmin = tokenFactory.getTokenAdministrador();
    }

    @Test
    void salvarTransacaoMockTest() throws Exception {

          TransacaoDTO transacaoDTO = TransacaoDTOFactory.getTransacaoDTO();

        String jsonRequest = objectMapper.writeValueAsString(transacaoDTO);

        TransacaoRecord transacaoRecord = new TransacaoRecord(1L, 1L, 1L, "Compra",
                new BigDecimal("10.10"), new BigDecimal("20.20"), LocalDateTime.now());


        Mockito.when(transacaoMockService.salvarTransacao(transacaoDTO)).thenReturn(transacaoRecord);

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + tokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();


        TransacaoRecord transacaoRequest = objectMapper.readValue(stringJson, TransacaoRecord.class);

        Assertions.assertEquals(transacaoRecord.id(), transacaoRequest.id());

    }

}
