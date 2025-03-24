package com.banco.xyz.financeiro.factory;

import com.banco.xyz.financeiro.model.Conta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ContaFactory {

    static Conta getConta(){

        Conta conta = new Conta();
        conta.setSaldo(new BigDecimal("10.00"));
        conta.setId(1L);
        conta.setDigito(1L);
        conta.setAgencia(1L);
        conta.setNumero(1L);
        conta.setDataCriacao(LocalDateTime.now());
        conta.setIdUsuario(1L);

        return conta;
    }
}
