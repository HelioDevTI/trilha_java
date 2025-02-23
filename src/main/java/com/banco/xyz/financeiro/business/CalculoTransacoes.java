package com.banco.xyz.financeiro.business;

import com.banco.xyz.financeiro.api.cambio.ApiExternaCambio;
import com.banco.xyz.financeiro.api.dto.ApiCambioDTO;
import com.banco.xyz.financeiro.enumeration.SiglasMoedas;
import com.banco.xyz.financeiro.model.Conta;
import com.banco.xyz.financeiro.repository.ContaRepository;
import com.banco.xyz.financeiro.repository.TipoTransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CalculoTransacoes {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ApiExternaCambio apiExternaCambio;


    @Autowired
    private TipoTransacaoRepository tipoTransacaoRepository;


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

    public BigDecimal calculoCambio(SiglasMoedas siglasMoedas, BigDecimal valor){

        ApiCambioDTO cambioDTO = apiExternaCambio.chamarAPIExternaCambios();

        BigDecimal valorEuroReal = cambioDTO.getMoedas().getReal();
        BigDecimal valorEuroDolar = cambioDTO.getMoedas().getDolar();
        BigDecimal valorEuroIene = cambioDTO.getMoedas().getIene();
        BigDecimal valorEuroPesoArg = cambioDTO.getMoedas().getPesoArgentino();

        return switch (siglasMoedas){
            case REAL -> valor;
            case DOLAR -> valor.multiply(valorEuroReal.multiply(BigDecimal.ONE.divide(valorEuroDolar, 20, RoundingMode.HALF_UP)))
                    .setScale(2, RoundingMode.HALF_UP);
            case EURO -> valorEuroReal.setScale(2, RoundingMode.HALF_UP);
            case IENE -> valor.multiply(valorEuroReal.multiply(BigDecimal.ONE.divide(valorEuroIene, 20, RoundingMode.HALF_UP)))
                    .setScale(2, RoundingMode.HALF_UP);
            case PESO_ARGENTINO -> valor.multiply(valorEuroReal.multiply(BigDecimal.ONE.divide(valorEuroPesoArg, 20, RoundingMode.HALF_UP)))
                    .setScale(2, RoundingMode.HALF_UP);
        };


    }

    public BigDecimal coversaoTransacao(BigDecimal valor, Long idTipoTransacao){

        String moeda = tipoTransacaoRepository.getReferenceById(idTipoTransacao).getMoeda();

        SiglasMoedas siglasMoedas = switch (moeda){
            case "R$" -> SiglasMoedas.REAL;
            case "US$" -> SiglasMoedas.DOLAR;
            case "€" -> SiglasMoedas.EURO;
            case "¥" -> SiglasMoedas.IENE;
            case "$" -> SiglasMoedas.PESO_ARGENTINO;
            default -> throw new IllegalStateException("Moeda não encontrada: " + moeda);
        };

        return calculoCambio(siglasMoedas, valor);
    }






}
