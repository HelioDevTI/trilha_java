package com.banco.xyz.financeiro.enumeration;

public enum SiglasMoedas {

    REAL("BRL", "R$", "Real"),
    DOLAR("USD", "US$", "Dolar"),
    EURO("EUR", "€", "Euro"),
    IENE("JPY", "¥", "Iene"),
    PESO_ARGENTINO("ARS", "$", "Peso Argentino");


    private final String sigla;

    private final String moeda;

    private final  String descricaoMoeda;

    SiglasMoedas(String sigla, String moeda, String descricaoMoeda){

        this.sigla = sigla;
        this.moeda = moeda;
        this.descricaoMoeda = descricaoMoeda;
    }
}
