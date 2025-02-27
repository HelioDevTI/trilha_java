package com.banco.xyz.financeiro.api.cambio;

import com.banco.xyz.financeiro.api.dto.ApiCambioDTO;
import com.banco.xyz.financeiro.api.dto.MoedasDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
@Slf4j
public class ApiMockCambio {

    @Value("${mock.cambio.url}")
    private String API_MOCK_URL;


    public ApiCambioDTO chamarAPIMockCambios(){

        ApiCambioDTO apiCambioDTO = new ApiCambioDTO();

        HttpURLConnection connection = null;


        try {
            URL url = new URL(API_MOCK_URL);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null){

                response.append(line);
            }

            bufferedReader.close();

            log.info("Response API = [{}]", response.toString());

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            try {
                apiCambioDTO = (objectMapper.readValue(response.toString(), ApiCambioDTO.class));
                apiCambioDTO.setMoedas(objectMapper.readValue(response.toString(), MoedasDTO.class));
            }catch (IOException e){
                log.error("Erro Mock API Mensagem [{}]", e);
                throw new RuntimeException(e);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            assert connection != null;
            connection.disconnect();
        }

        return apiCambioDTO;
    }

}
