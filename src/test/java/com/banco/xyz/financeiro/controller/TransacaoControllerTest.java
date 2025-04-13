package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.dto.TransacaoAtualizarDTO;
import com.banco.xyz.financeiro.dto.TransacaoDTO;
import com.banco.xyz.financeiro.enumeration.Mes;
import com.banco.xyz.financeiro.factory.DadosMetricasTransacaoProxyFactory;
import com.banco.xyz.financeiro.factory.TokenFactory;
import com.banco.xyz.financeiro.factory.TransacaoAtualizarDTOFactory;
import com.banco.xyz.financeiro.factory.TransacaoDTOFactory;
import com.banco.xyz.financeiro.proxy.DadosMetricasTransacaoProxy;
import com.banco.xyz.financeiro.proxy.DadosTransacaoProxy;
import com.banco.xyz.financeiro.recod.TransacaoRecord;
import com.banco.xyz.financeiro.service.TransacaoService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
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
public class TransacaoControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenFactory tokenFactory;

    @MockitoBean
    private TransacaoService transacaoService;


    private static final String URL = "/transacao";

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
    void consultaTransacaoTest() throws Exception {

        TransacaoRecord transacaoRecord = new TransacaoRecord(1L, 1L, 1L, "Compra",
                new BigDecimal("20.0"), new BigDecimal("30.00"), LocalDateTime.now());

        Mockito.when(transacaoService.getTransacao(1L)).thenReturn(transacaoRecord);

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + tokenGeren))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();


        TransacaoRecord transacaoRecordRetorno = objectMapper.readValue(stringJson, TransacaoRecord.class);

        Assertions.assertEquals(transacaoRecord.id(), transacaoRecordRetorno.id());

    }

    @Test
    void listaContaTest() throws Exception {

        TransacaoRecord transacaoRecord = new TransacaoRecord(1L, 1L, 1L, "Compra",
                new BigDecimal("20.0"), new BigDecimal("30.00"), LocalDateTime.now());

        TransacaoRecord transacaoRecord2 = new TransacaoRecord(2L, 2L, 2L, "Compra 2",
                new BigDecimal("50.0"), new BigDecimal("60.00"), LocalDateTime.now());

        List<TransacaoRecord> listaTransa = List.of(transacaoRecord, transacaoRecord2);


        Pageable pageable = PageRequest.of(0, 2);
        Page<TransacaoRecord> page = new PageImpl<>(listaTransa, pageable, listaTransa.size());

        Mockito.when(transacaoService.listaTransacao(Mockito.any())).thenReturn(page);

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
    void consultaTransacaoConstomizadaTest() throws Exception {

        DadosTransacaoProxy dadosTransacaoProxy = new DadosTransacaoProxy() {
            @Override
            public Long getNumero() {
                return 1L;
            }

            @Override
            public Long getDigito() {
                return 1L;
            }

            @Override
            public Long getAgencia() {
                return 1L;
            }

            @Override
            public LocalDateTime getData() {
                return LocalDateTime.now();
            }

            @Override
            public String getTipoTransacao() {
                return "Compra";
            }

            @Override
            public String getDescricao() {
                return "Compra compra";
            }

            @Override
            public String getValor() {
                return "10.00";
            }

            @Override
            public String getValorConvertido() {
                return "1000.00";
            }
        };

        DadosTransacaoProxy dadosTransacaoProxy2 = new DadosTransacaoProxy() {
            @Override
            public Long getNumero() {
                return 2L;
            }

            @Override
            public Long getDigito() {
                return 2L;
            }

            @Override
            public Long getAgencia() {
                return 2L;
            }

            @Override
            public LocalDateTime getData() {
                return LocalDateTime.now();
            }

            @Override
            public String getTipoTransacao() {
                return "Compra 2";
            }

            @Override
            public String getDescricao() {
                return "Compra2 compra2";
            }

            @Override
            public String getValor() {
                return "50.00";
            }

            @Override
            public String getValorConvertido() {
                return "5000.00";
            }
        };



        List<DadosTransacaoProxy> listaTransa = List.of(dadosTransacaoProxy, dadosTransacaoProxy2);

        Mockito.when(transacaoService.consultaTransacao(1L, null, null,
                null, null)).thenReturn(listaTransa);

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/consulta?IdConta=1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + tokenCorren))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();

        JsonNode rootNode = objectMapper.readTree(stringJson);
        String tipoTransacao = rootNode.get(0).get("tipoTransacao").asText();

        Assertions.assertEquals("Compra", tipoTransacao);

    }

    @Test
    void salvarTransacaoTest() throws Exception {



        TransacaoDTO transacaoDTO = TransacaoDTOFactory.getTransacaoDTO();
        TransacaoRecord transacaoRecord = new TransacaoRecord(transacaoDTO.getIdTipo(), transacaoDTO.getIdTipo(),
                transacaoDTO.getIdConta(), transacaoDTO.getDescricao(), transacaoDTO.getValor(),
                 new BigDecimal("20.00"), LocalDateTime.now());

        String jsonRequest = objectMapper.writeValueAsString(transacaoDTO);


        Mockito.when(transacaoService.salvarTransacao(transacaoDTO)).thenReturn(transacaoRecord);

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + tokenCorren))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();

        TransacaoRecord transacaoRecordRetorno = objectMapper.readValue(stringJson, TransacaoRecord.class);

        Assertions.assertEquals(transacaoRecord.id(), transacaoRecordRetorno.id());

    }

    @Test
    void atualizarTransacaoTest() throws Exception {


        TransacaoAtualizarDTO transacaoDTO = TransacaoAtualizarDTOFactory.getTransacaoAtualizarDTO();
        TransacaoRecord transacaoRecord = new TransacaoRecord(transacaoDTO.getIdTipo(), transacaoDTO.getIdTipo(),
                1L, transacaoDTO.getDescricao(), transacaoDTO.getValor(),
                new BigDecimal("20.00"), LocalDateTime.now());

        String jsonRequest = objectMapper.writeValueAsString(transacaoDTO);


        Mockito.when(transacaoService.atualizarTransacao(transacaoDTO)).thenReturn(transacaoRecord);

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + tokenGeren))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();

        TransacaoRecord transacaoRecordRetorno = objectMapper.readValue(stringJson, TransacaoRecord.class);

        Assertions.assertEquals(transacaoRecord.id(), transacaoRecordRetorno.id());

    }

    @Test
    void excluirTransacaoTest() throws Exception {

        String mensagemRetorno = "Transacao excluida com sucesso";


        Mockito.when(transacaoService.excluirTransacao(1L)).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).body(mensagemRetorno));

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.delete(URL.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + tokenGeren))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();

        Assertions.assertEquals(mensagemRetorno, stringJson);

    }

    @Test
    void consultaMetricasTransacaoTest() throws Exception {

        List<DadosMetricasTransacaoProxy> listaDados = DadosMetricasTransacaoProxyFactory.getDadosMetricasTransacaoProxy();

        Mockito.when(transacaoService.consultaMetricasTransacoes(1L, Mes.JANEIRO, 2025L)).thenReturn(listaDados);

        MvcResult json = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/consulta/metricas/conta/1/mes/JANEIRO/ano/2025"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + tokenGeren))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String stringJson = json.getResponse().getContentAsString();
        ArrayNode jsonArray = (ArrayNode) objectMapper.readTree(stringJson);

        Assertions.assertEquals(listaDados.get(0).getCodigo(), jsonArray.get(0).get("codigo").asLong());

    }
}
