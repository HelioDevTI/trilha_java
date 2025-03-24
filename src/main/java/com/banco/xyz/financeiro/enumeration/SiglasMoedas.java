package com.banco.xyz.financeiro.enumeration;

import lombok.Getter;

public enum SiglasMoedas {

    REAL("BRL", "R$", "Real"),
    DOLAR("USD", "US$", "Dolar"),
    EURO("EUR", "€", "Euro"),
    IENE("JPY", "¥", "Iene"),
    PESO_ARGENTINO("ARS", "$", "Peso Argentino");

    @Getter
    private final String sigla;

    @Getter
    private final String moeda;

    @Getter
    private final  String descricaoMoeda;

    SiglasMoedas(String sigla, String moeda, String descricaoMoeda){

        this.sigla = sigla;
        this.moeda = moeda;
        this.descricaoMoeda = descricaoMoeda;
    }


}
