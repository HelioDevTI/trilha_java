package com.banco.xyz.financeiro.factory;

import com.banco.xyz.financeiro.api.dto.MoedasDTO;

import java.math.BigDecimal;

public interface MoedasDTOFactory {

    static MoedasDTO getMoedasDTO(){

        MoedasDTO moedasDTO = new MoedasDTO();
        moedasDTO.setDolar(new BigDecimal("2.0"));
        moedasDTO.setIene(new BigDecimal("3.0"));
        moedasDTO.setReal(new BigDecimal("4.0"));
        moedasDTO.setPesoArgentino(new BigDecimal("5.0"));

        return moedasDTO;
    }
}
