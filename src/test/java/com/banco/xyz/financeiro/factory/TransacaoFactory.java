package com.banco.xyz.financeiro.factory;

import com.banco.xyz.financeiro.model.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TransacaoFactory {


    static Transacao getTransacao(){

        Transacao transacao = new Transacao();
        transacao.setIdConta(1L);
        transacao.setDataTransacao(LocalDateTime.now());
        transacao.setDescricao("Nova Transacao");
        transacao.setId(1L);
        transacao.setIdTipo(1L);
        transacao.setValorConvertido(new BigDecimal("100.11"));
        transacao.setValor(new BigDecimal("100.00"));


        return transacao;
    }
}
