package com.banco.xyz.financeiro.proxy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DadosTrasacaoProxy {

    Long getNumero();

    Long getDigito();

    Long getAgencia();

    LocalDateTime getData();

    String getTipoTransacao();

    String getDescricao();

    String getValor();

    String getValorConvertido();

}
