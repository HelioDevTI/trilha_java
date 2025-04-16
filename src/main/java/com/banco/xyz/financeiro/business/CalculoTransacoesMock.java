package com.banco.xyz.financeiro.business;

import com.banco.xyz.financeiro.api.cambio.ApiMockCambio;
import com.banco.xyz.financeiro.api.dto.ApiCambioDTO;
import com.banco.xyz.financeiro.enumeration.SiglasMoedas;
import com.banco.xyz.financeiro.repository.TipoTransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Component
public class CalculoTransacoesMock {


    @Autowired
    private ApiMockCambio apiMockCambio;

    @Autowired
    private TipoTransacaoRepository tipoTransacaoRepository;


    public BigDecimal calculoCambioMock(SiglasMoedas siglasMoedas, BigDecimal valor) throws NoSuchAlgorithmException, KeyManagementException {

        ApiCambioDTO cambioDTO = apiMockCambio.chamarAPIMockCambios();

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

    public BigDecimal coversaoTransacaoMock(BigDecimal valor, Long idTipoTransacao) throws NoSuchAlgorithmException, KeyManagementException {

        String moeda = tipoTransacaoRepository.getReferenceById(idTipoTransacao).getMoeda();

        SiglasMoedas siglasMoedas = switch (moeda){
            case "R$" -> SiglasMoedas.REAL;
            case "US$" -> SiglasMoedas.DOLAR;
            case "€" -> SiglasMoedas.EURO;
            case "¥" -> SiglasMoedas.IENE;
            case "$" -> SiglasMoedas.PESO_ARGENTINO;
            default -> throw new IllegalStateException("Moeda não encontrada: " + moeda);
        };

        return calculoCambioMock(siglasMoedas, valor);
    }

}
