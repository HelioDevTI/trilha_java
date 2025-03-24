package com.banco.xyz.financeiro.factory;

import com.banco.xyz.financeiro.enumeration.SiglasMoedas;
import com.banco.xyz.financeiro.enumeration.Tipo;
import com.banco.xyz.financeiro.model.TipoTransacao;

import java.time.LocalDateTime;

public interface TipoTransacaoFactory {

    static TipoTransacao getTipoTransacao(){

        TipoTransacao tipoTransacao = new TipoTransacao();
        tipoTransacao.setDataCriacao(LocalDateTime.now());
        tipoTransacao.setTipo(Tipo.DEBITO);
        tipoTransacao.setId(1L);
        tipoTransacao.setMoeda(SiglasMoedas.REAL.getMoeda());
        tipoTransacao.setAtivo(true);
        tipoTransacao.setDescricao("PIX");

        return tipoTransacao;
    }
}
