package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.dto.ContaAtualizarDTO;
import com.banco.xyz.financeiro.dto.ContaDTO;
import com.banco.xyz.financeiro.factory.ContaAtualizarDTOFactory;
import com.banco.xyz.financeiro.factory.ContaDTOFactory;
import com.banco.xyz.financeiro.factory.TokenFactory;
import com.banco.xyz.financeiro.recod.ContaRecord;
import com.banco.xyz.financeiro.service.ContaService;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ContaControllerTest {

    @MockitoBean
    private ContaService contaService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenFactory tokenFactory;



    private static final String URL = "/conta";

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
    void consultaContaTest() throws Exception {

        ContaRecord contaRequest = new ContaRecord(1L, 1L, 1L, 1L, 1L,
                new BigDecimal("300.55"), LocalDateTime.now());

        Mockito.when(contaService.getConta(1L)).thenReturn(contaRequest);

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + tokenCorren))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();


        ContaRecord contaResponse = objectMapper.readValue(stringJson, ContaRecord.class);

        Assertions.assertEquals(contaResponse.numero(), contaRequest.numero());

    }


    @Test
    void listaContaTest() throws Exception {

        ContaRecord contaRequest = new ContaRecord(1L, 1L, 1L, 1L, 1L,
                new BigDecimal("300.55"), LocalDateTime.now());

        ContaRecord contaRequest2 = new ContaRecord(2L, 2L, 2L, 2L, 2L,
                new BigDecimal("600.66"), LocalDateTime.now());

        List<ContaRecord> listaConta = List.of(contaRequest, contaRequest2);


        Pageable pageable = PageRequest.of(0, 2);
        Page<ContaRecord> page = new PageImpl<>(listaConta, pageable, listaConta.size());

        Mockito.when(contaService.listaConta(Mockito.any())).thenReturn(page);

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
    void salvarContaTest() throws Exception {



        ContaDTO contaDTO = ContaDTOFactory.getContaDTO();
        ContaRecord contaRecord = new ContaRecord(1L, contaDTO.getIdUsuario(), contaDTO.getNumero(), contaDTO.getDigito(),
                contaDTO.getAgencia(), contaDTO.getSaldo(), LocalDateTime.now());

        String jsonRequest = objectMapper.writeValueAsString(contaDTO);


        Mockito.when(contaService.salvarConta(contaDTO)).thenReturn(contaRecord);

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest)
                .header("Authorization", "Bearer " + tokenCorren))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();


        ContaRecord contaResponse = objectMapper.readValue(stringJson, ContaRecord.class);

        Assertions.assertEquals(contaRecord.numero(), contaResponse.numero());

    }

    @Test
    void atualizarContaTest() throws Exception {

        String mensagemRetorno = "Conta atualizada com sucesso";

        ContaAtualizarDTO contaAtualizarDTO = ContaAtualizarDTOFactory.getContaAtualizarDTO();
        ContaRecord contaRecord = new ContaRecord(contaAtualizarDTO.getId(), contaAtualizarDTO.getIdUsuario(),
                contaAtualizarDTO.getNumero(), contaAtualizarDTO.getDigito(),  contaAtualizarDTO.getAgencia(),
                contaAtualizarDTO.getSaldo(), LocalDateTime.now());

        String jsonRequest = objectMapper.writeValueAsString(contaAtualizarDTO);


        Mockito.when(contaService.atualizarConta(contaAtualizarDTO)).thenReturn(contaRecord);

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + tokenGeren))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();

        ContaRecord contaResponse = objectMapper.readValue(stringJson, ContaRecord.class);

        Assertions.assertEquals(contaRecord.numero(), contaResponse.numero());

    }

    @Test
    void excluirContaTest() throws Exception {

        String mensagemRetorno = "Conta excluida com sucesso";


        Mockito.when(contaService.excluirConta(1L)).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).body(mensagemRetorno));

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.delete(URL.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + tokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();

        Assertions.assertEquals(mensagemRetorno, stringJson);

    }


}
