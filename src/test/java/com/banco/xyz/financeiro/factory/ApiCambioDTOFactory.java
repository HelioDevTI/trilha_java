package com.banco.xyz.financeiro.factory;

import com.banco.xyz.financeiro.api.dto.ApiCambioDTO;

import java.time.LocalDate;

public interface ApiCambioDTOFactory {

    static ApiCambioDTO getApiCambioDTO(){

        ApiCambioDTO apiCambioDTO = new ApiCambioDTO();
        apiCambioDTO.setSuccess(true);
        apiCambioDTO.setTimestamp(123456789L);
        apiCambioDTO.setBase("EUR");
        apiCambioDTO.setDate(LocalDate.of(2024, 1, 1));
        apiCambioDTO.setMoedas(MoedasDTOFactory.getMoedasDTO());

        return apiCambioDTO;

    }
}
