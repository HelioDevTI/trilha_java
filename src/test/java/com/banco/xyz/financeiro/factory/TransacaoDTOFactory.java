package com.banco.xyz.financeiro.factory;

import com.banco.xyz.financeiro.dto.TransacaoDTO;

import java.math.BigDecimal;

public interface TransacaoDTOFactory {

    static TransacaoDTO getTransacaoDTO(){

        TransacaoDTO transacaoDTO = new TransacaoDTO();
        transacaoDTO.setIdTipo(1L);
        transacaoDTO.setIdConta(1L);
        transacaoDTO.setDescricao("Compra");
        transacaoDTO.setValor(new BigDecimal("15.15"));

        return transacaoDTO;
    }
}
