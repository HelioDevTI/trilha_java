package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.dto.TipoTransacaoAtuliDTO;
import com.banco.xyz.financeiro.dto.TipoTransacaoDTO;
import com.banco.xyz.financeiro.enumeration.Tipo;
import com.banco.xyz.financeiro.factory.TokenFactory;
import com.banco.xyz.financeiro.recod.TipoTransacaoRecord;
import com.banco.xyz.financeiro.service.TipoTransacaoService;
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
public class TipoTransacaoControllerTest {

    @MockitoBean
    private TipoTransacaoService tipoTransacaoService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenFactory tokenFactory;


    private static final String URL = "/tipo/transacao";


    private static String tokenAdmin;

    @BeforeAll
    static void setUpAll(
            @Autowired TokenFactory tokenFactory
    ) {
        tokenAdmin = tokenFactory.getTokenAdministrador();
    }


    @Test
    void consultaTipoTransacaoTest() throws Exception {

        TipoTransacaoRecord tipoTransacaoRecord = new TipoTransacaoRecord(1L, "$$$", "Dinheiro", true,
                Tipo.DEBITO, LocalDateTime.now());

        Mockito.when(tipoTransacaoService.getTipoTransacao(1L)).thenReturn(tipoTransacaoRecord);

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + tokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();


        TipoTransacaoRecord tipoTransacaoRecordResponse = objectMapper.readValue(stringJson, TipoTransacaoRecord.class);

        Assertions.assertEquals(tipoTransacaoRecord.tipo(), tipoTransacaoRecordResponse.tipo());

    }


    @Test
    void listaTipoTransacaoTest() throws Exception {

        TipoTransacaoRecord tipoTransacaoRecord = new TipoTransacaoRecord(1L, "$$$", "Dinheiro", true,
                Tipo.DEBITO, LocalDateTime.now());

        TipoTransacaoRecord tipoTransacaoRecord2 = new TipoTransacaoRecord(1L, "RRR", "Dinheiro REX", true,
                Tipo.CREDITO, LocalDateTime.now());

        List<TipoTransacaoRecord> listaTipoTransa = List.of(tipoTransacaoRecord, tipoTransacaoRecord2);


        Pageable pageable = PageRequest.of(0, 2);
        Page<TipoTransacaoRecord> page = new PageImpl<>(listaTipoTransa, pageable, listaTipoTransa.size());

        Mockito.when(tipoTransacaoService.listaTipoTransacao(Mockito.any())).thenReturn(page);

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
    void salvarTipoTransacaoTest() throws Exception {

        TipoTransacaoRecord tipoTransacaoRecord = new TipoTransacaoRecord(1L, "$$$", "Dinheiro", true,
                Tipo.DEBITO, LocalDateTime.now());

        TipoTransacaoDTO tipoTransacaoDTO = new TipoTransacaoDTO();
        tipoTransacaoDTO.setTipo(Tipo.DEBITO);
        tipoTransacaoDTO.setDescricao("Nova Dinheiro");
        tipoTransacaoDTO.setMoeda("$$$");
        tipoTransacaoDTO.setAtivo(true);

        String jsonRequest = objectMapper.writeValueAsString(tipoTransacaoDTO);


        Mockito.when(tipoTransacaoService.salvarTipoTransacao(tipoTransacaoDTO)).thenReturn(tipoTransacaoRecord);

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + tokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();

        TipoTransacaoRecord tipoTransacaoRecordResponse = objectMapper.readValue(stringJson, TipoTransacaoRecord.class);

        Assertions.assertEquals(tipoTransacaoRecord.tipo(), tipoTransacaoRecordResponse.tipo());

    }

    @Test
    void atualizarTipoTransacaoTest() throws Exception {

        TipoTransacaoRecord tipoTransacaoRecord = new TipoTransacaoRecord(1L, "$$$", "Dinheiro", true,
                Tipo.DEBITO, LocalDateTime.now());

        TipoTransacaoAtuliDTO tipoTransacaoAtuliDTO = new TipoTransacaoAtuliDTO();
        tipoTransacaoAtuliDTO.setTipo(Tipo.DEBITO);
        tipoTransacaoAtuliDTO.setDescricao("Nova Dinheiro");
        tipoTransacaoAtuliDTO.setMoeda("$$$");
        tipoTransacaoAtuliDTO.setAtivo(true);
        tipoTransacaoAtuliDTO.setId(1L);

        String jsonRequest = objectMapper.writeValueAsString(tipoTransacaoAtuliDTO);


        Mockito.when(tipoTransacaoService.atualizarTipoTransacao(tipoTransacaoAtuliDTO)).thenReturn(tipoTransacaoRecord);

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + tokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();

        TipoTransacaoRecord tipoTransacaoRecordResponse = objectMapper.readValue(stringJson, TipoTransacaoRecord.class);

        Assertions.assertEquals(tipoTransacaoRecord.tipo(), tipoTransacaoRecordResponse.tipo());

    }

    @Test
    void excluirContaTest() throws Exception {

        String mensagemRetorno = "Tipo Transacao excluida com sucesso";


        Mockito.when(tipoTransacaoService.excluirTipoTransacao(1L))
                .thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).body(mensagemRetorno));

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.delete(URL.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + tokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();

        Assertions.assertEquals(mensagemRetorno, stringJson);

    }
}
