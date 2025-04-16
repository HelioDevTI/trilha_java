package com.banco.xyz.financeiro.api.cambio;

import com.banco.xyz.financeiro.api.dto.ApiCambioDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
@Slf4j
public class ApiExternaCambio {

    @Value("${cambio.url}")
    private String API_URL;

    private static final String PARAM1 ="access_key=";

    private static final String PARAM2 ="&symbols=";

    @Value("${cambio.chave}")
    private String chaveAcesso;

    @Value("${cambio.simbolos}")
    private String simbolos;

    @Autowired
    ApiMockCambio apiMockCambio;


    public ApiCambioDTO chamarAPIExternaCambios(){

        ApiCambioDTO apiCambioDTO;

        HttpURLConnection connection = null;

        String urlString = API_URL + PARAM1 + chaveAcesso + PARAM2 + simbolos;

        log.info("URL API = [{}]", urlString);

        try {
            URL url = new URL(urlString);
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
                apiCambioDTO = objectMapper.readValue(response.toString(), ApiCambioDTO.class);
            }catch (IOException e){
                log.error("Erro para Chamar API Erro [{}]", e.getMessage());
                throw new RuntimeException(e);
            }


        } catch (IOException e)  {
            log.error("Erro  BufferedReade Erro = [{}]", e.getMessage());

            //Se o erro For que excedeu a quantidade de solicitacao utiliza o MockAPI
            if(e.getMessage().contains("code: 429")){
                log.info("Utilizando Mock API Pois API Externa Atingiu a quantidade de Solicitacoes");
                return apiMockCambio.chamarAPIMockCambios();
            }

            throw new RuntimeException(e);
        } finally {
            assert connection != null;
            connection.disconnect();
        }

        return apiCambioDTO;
    }


}
