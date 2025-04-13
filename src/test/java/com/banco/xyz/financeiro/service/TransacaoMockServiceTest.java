package com.banco.xyz.financeiro.service;


import com.banco.xyz.financeiro.business.CalculoTransacoesMock;
import com.banco.xyz.financeiro.dto.TransacaoDTO;
import com.banco.xyz.financeiro.factory.TransacaoDTOFactory;
import com.banco.xyz.financeiro.model.Transacao;
import com.banco.xyz.financeiro.recod.TransacaoRecord;
import com.banco.xyz.financeiro.repository.TransacaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
@ActiveProfiles("test")
public class TransacaoMockServiceTest {

    @MockitoBean
    private TransacaoRepository transacaoRepository;

    @MockitoBean
    private CalculoTransacoesMock calculoTransacoesMock;

    @Autowired
    private TransacaoMockService transacaoMockService;


    @Test
    void salvarTransacaoTest(){

        BigDecimal valorConversao = new BigDecimal("999999.99");

        TransacaoDTO transacaoDTO = TransacaoDTOFactory.getTransacaoDTO();

        Transacao transacao = new Transacao();
        transacao.setId(1L);
        transacao.setDataTransacao(LocalDateTime.now());
        transacao.setDescricao(transacaoDTO.getDescricao());
        transacao.setValor(new BigDecimal("0.10"));
        transacao.setValorConvertido(valorConversao);
        transacao.setIdTipo(transacaoDTO.getIdTipo());
        transacao.setIdConta(transacaoDTO.getIdConta());


        Mockito.when(calculoTransacoesMock
                .coversaoTransacaoMock(transacaoDTO.getValor(), transacaoDTO.getIdTipo()))
                        .thenReturn(valorConversao);

        Mockito.when(transacaoRepository.save(Mockito.any())).thenReturn(transacao);

        TransacaoRecord transacaoRecord = transacaoMockService.salvarTransacao(transacaoDTO);

        Assertions.assertEquals(valorConversao, transacaoRecord.valorConvertido());
    }


}
