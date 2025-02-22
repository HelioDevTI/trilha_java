package com.banco.xyz.financeiro.business;

import com.banco.xyz.financeiro.model.Conta;
import com.banco.xyz.financeiro.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Component
public class CalculoTransacoes {

    @Autowired
    private ContaRepository contaRepository;


    public Boolean atualizaSaldo(Long idConta, BigDecimal valorTransacao){

        Conta conta = contaRepository.getReferenceById(idConta);

        if(conta.getId() == null){
            return false;
        }


       BigDecimal valorSaldo = conta.getSaldo().subtract(valorTransacao);

        if(valorSaldo.compareTo(BigDecimal.ZERO) < 0){
            return false;
        }

        conta.setSaldo(valorSaldo);
        contaRepository.save(conta);


        return true;
    }


}
