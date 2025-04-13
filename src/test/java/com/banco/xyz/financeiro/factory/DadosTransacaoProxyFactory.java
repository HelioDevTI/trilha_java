package com.banco.xyz.financeiro.factory;

import com.banco.xyz.financeiro.proxy.DadosTransacaoProxy;

import java.time.LocalDateTime;
import java.util.List;

public interface DadosTransacaoProxyFactory {


    static List<DadosTransacaoProxy> getListaDadosTransacaoProxy(){

        DadosTransacaoProxy dadosTransacaoProxy = new DadosTransacaoProxy() {
            @Override
            public Long getNumero() {
                return 1L;
            }

            @Override
            public Long getDigito() {
                return 1L;
            }

            @Override
            public Long getAgencia() {
                return 1L;
            }

            @Override
            public LocalDateTime getData() {
                return LocalDateTime.now();
            }

            @Override
            public String getTipoTransacao() {
                return "Nova";
            }

            @Override
            public String getDescricao() {
                return "Nova Compra";
            }

            @Override
            public String getValor() {
                return "100.00";
            }

            @Override
            public String getValorConvertido() {
                return "200.00";
            }
        };

        DadosTransacaoProxy dadosTransacaoProxy2 = new DadosTransacaoProxy() {
            @Override
            public Long getNumero() {
                return 2L;
            }

            @Override
            public Long getDigito() {
                return 2L;
            }

            @Override
            public Long getAgencia() {
                return 2L;
            }

            @Override
            public LocalDateTime getData() {
                return LocalDateTime.now();
            }

            @Override
            public String getTipoTransacao() {
                return "Novo 2";
            }

            @Override
            public String getDescricao() {
                return "Compra 2";
            }

            @Override
            public String getValor() {
                return "900.00";
            }

            @Override
            public String getValorConvertido() {
                return "999.99";
            }
        };

        return List.of(dadosTransacaoProxy, dadosTransacaoProxy2);
    }
}
