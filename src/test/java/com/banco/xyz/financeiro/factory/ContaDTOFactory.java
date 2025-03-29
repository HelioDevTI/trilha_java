package com.banco.xyz.financeiro.factory;

import com.banco.xyz.financeiro.dto.ContaDTO;

import java.math.BigDecimal;

public interface ContaDTOFactory {

    static ContaDTO getContaDTO(){

        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setAgencia(1L);
        contaDTO.setDigito(1L);
        contaDTO.setNumero(1L);
        contaDTO.setIdUsuario(1L);
        contaDTO.setSaldo(new BigDecimal("100.11"));

        return contaDTO;
    }
}
