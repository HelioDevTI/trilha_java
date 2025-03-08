package com.banco.xyz.financeiro.proxy;

import java.time.LocalDateTime;

public interface DadosTransacaoProxy {

    Long getNumero();

    Long getDigito();

    Long getAgencia();

    LocalDateTime getData();

    String getTipoTransacao();

    String getDescricao();

    String getValor();

    String getValorConvertido();

}
