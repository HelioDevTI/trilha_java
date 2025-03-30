package com.banco.xyz.financeiro.factory;

import com.banco.xyz.financeiro.dto.ContaAtualizarDTO;

import java.math.BigDecimal;

public interface ContaAtualizarDTOFactory {

    static ContaAtualizarDTO getContaAtualizarDTO(){

        ContaAtualizarDTO contaAtualizarDTO = new ContaAtualizarDTO();
        contaAtualizarDTO.setAgencia(1L);
        contaAtualizarDTO.setDigito(1L);
        contaAtualizarDTO.setNumero(1L);
        contaAtualizarDTO.setIdUsuario(1L);
        contaAtualizarDTO.setSaldo(new BigDecimal("100.11"));
        contaAtualizarDTO.setId(1L);

        return contaAtualizarDTO;
    }
}
