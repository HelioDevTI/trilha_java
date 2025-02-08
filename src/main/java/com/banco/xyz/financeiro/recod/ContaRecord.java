package com.banco.xyz.financeiro.recod;

import com.banco.xyz.financeiro.model.Conta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ContaRecord(Long id, Long idUsuario, Long numero, Long digito, Long agencia,
                          BigDecimal saldo, LocalDateTime dataCriacao) {


    public ContaRecord(Conta conta){

        this(conta.getId(), conta.getIdUsuario(), conta.getNumero(), conta.getDigito(),
                conta.getAgencia(), conta.getSaldo(), conta.getDataCriacao());
    }

}
