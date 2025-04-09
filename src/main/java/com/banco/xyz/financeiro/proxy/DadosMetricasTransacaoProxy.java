package com.banco.xyz.financeiro.proxy;

import java.math.BigDecimal;

public interface DadosMetricasTransacaoProxy {

    Long getCodigo();
    String getMoeda();
    String getTipo();
    String getTransacao();
    Long getQuantidade();
    BigDecimal getTotal();
    Double getPorcentagem();

}
