package com.banco.xyz.financeiro.service;

import com.banco.xyz.financeiro.business.CalculoTransacoesMock;
import com.banco.xyz.financeiro.dto.TransacaoDTO;
import com.banco.xyz.financeiro.model.Transacao;
import com.banco.xyz.financeiro.recod.TransacaoRecord;
import com.banco.xyz.financeiro.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransacaoMockService {


    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private CalculoTransacoesMock calculoTransacoesMock;


    public TransacaoRecord salvarTransacao(TransacaoDTO transacaoDTO){


        Transacao transacao = new Transacao();
        transacao.setIdConta(transacaoDTO.getIdConta());
        transacao.setIdTipo(transacaoDTO.getIdTipo());
        transacao.setDescricao(transacaoDTO.getDescricao());
        transacao.setValor(transacaoDTO.getValor());
        transacao.setDataTransacao(LocalDateTime.now());
        transacao.setValorConvertido(calculoTransacoesMock.coversaoTransacaoMock(transacaoDTO.getValor(), transacaoDTO.getIdTipo()));
        Transacao trans = transacaoRepository.save(transacao);

        return new TransacaoRecord(trans.getId(), trans.getIdTipo(), trans.getIdConta(), trans.getDescricao(), trans.getValor(),
                trans.getValorConvertido(), trans.getDataTransacao());
    }


}
