package com.banco.xyz.financeiro.factory;

import com.banco.xyz.financeiro.dto.TransacaoAtualizarDTO;

import java.math.BigDecimal;

public interface TransacaoAtualizarDTOFactory {

    static TransacaoAtualizarDTO getTransacaoAtualizarDTO(){

        TransacaoAtualizarDTO transacaoAtualizarDTO = new TransacaoAtualizarDTO();
        transacaoAtualizarDTO.setId(1L);
        transacaoAtualizarDTO.setIdTipo(1L);
        transacaoAtualizarDTO.setDescricao("Compra");
        transacaoAtualizarDTO.setValor(new BigDecimal("35.35"));

        return transacaoAtualizarDTO;
    }
}
