package com.banco.xyz.financeiro.api.cambio;

import com.banco.xyz.financeiro.api.dto.ApiCambioDTO;
import com.banco.xyz.financeiro.api.dto.MoedasDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Component
@Slf4j
public class ApiMockCambio {

    @Value("${mock.cambio.url}")
    private String API_MOCK_URL;


    public ApiCambioDTO chamarAPIMockCambios() throws NoSuchAlgorithmException, KeyManagementException {

        ApiCambioDTO apiCambioDTO = new ApiCambioDTO();

        HttpURLConnection connection = null;

        // Criar um array contendo sua implementação de TrustManager
        TrustManager[] trustAllCerts = new TrustManager[]{new TrustAllCertificates()};

        // Obter o contexto SSL padrão
        SSLContext sslContext = SSLContext.getInstance("SSL");

        // Inicializar o contexto SSL com o TrustManager que confia em todos os certificados
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        // Definir o SocketFactory para o HttpsURLConnection
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        // Definir o HostnameVerifier para o HttpsURLConnection
        HttpsURLConnection.setDefaultHostnameVerifier(new TrustAllHostnames());



        try {
            URL url = new URL(API_MOCK_URL);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            log.info("Consulta no Mock API URL [{}]", API_MOCK_URL);

            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()));

            log.info("Chamando Builder");
            StringBuilder response = new StringBuilder();
            String line;

            log.info("Executar Wile");
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
            log.error("Execao IO erro [{}]", e);
            throw new RuntimeException(e);
        } finally {
            assert connection != null;
            connection.disconnect();
        }

        return apiCambioDTO;
    }

}
