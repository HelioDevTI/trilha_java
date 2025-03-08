package com.banco.xyz.financeiro.proxy;

import java.time.LocalDateTime;

public interface DadosRelatorioTransacaoProxy {

    LocalDateTime getData();

    String getTipoTransacao();

    String getTipoCobranca();

    String getDescricao();

    String getValorCompra();

    String getValorReais();

}
