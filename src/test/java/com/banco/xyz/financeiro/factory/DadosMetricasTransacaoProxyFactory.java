package com.banco.xyz.financeiro.factory;

import com.banco.xyz.financeiro.proxy.DadosMetricasTransacaoProxy;

import java.math.BigDecimal;
import java.util.List;

public interface DadosMetricasTransacaoProxyFactory {


    static List<DadosMetricasTransacaoProxy> getDadosMetricasTransacaoProxy(){

        DadosMetricasTransacaoProxy dados = new DadosMetricasTransacaoProxy() {
            @Override
            public Long getCodigo() {
                return 1L;
            }

            @Override
            public String getMoeda() {
                return "RR$$";
            }

            @Override
            public String getTipo() {
                return "Compra";
            }

            @Override
            public String getTransacao() {
                return "Compra Mercado";
            }

            @Override
            public Long getQuantidade() {
                return 10L;
            }

            @Override
            public BigDecimal getTotal() {
                return new BigDecimal("1000.00");
            }

            @Override
            public Double getPorcentagem() {
                return 50.0;
            }
        };

        DadosMetricasTransacaoProxy dados2 = new DadosMetricasTransacaoProxy() {
            @Override
            public Long getCodigo() {
                return 2L;
            }

            @Override
            public String getMoeda() {
                return "EE$$";
            }

            @Override
            public String getTipo() {
                return "Compra 2";
            }

            @Override
            public String getTransacao() {
                return "Compra Bar";
            }

            @Override
            public Long getQuantidade() {
                return 800L;
            }

            @Override
            public BigDecimal getTotal() {
                return new BigDecimal("10000.00");
            }

            @Override
            public Double getPorcentagem() {
                return 80.0;
            }
        };

        return List.of(dados, dados2);
    }
}
