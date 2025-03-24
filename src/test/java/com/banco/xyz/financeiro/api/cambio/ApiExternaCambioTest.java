package com.banco.xyz.financeiro.api.cambio;

import com.banco.xyz.financeiro.api.dto.ApiCambioDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApiExternaCambioTest {

    @InjectMocks
    private ApiExternaCambio apiExternaCambio;

    @Mock
    private HttpURLConnection connection;

    @Mock
    private URL url;

    @Mock
    private BufferedReader bufferedReader;

    private String apiUrl = "http://example.com/api";
    private String chaveAcesso = "testKey";
    private String simbolos = "USD,EUR";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(apiExternaCambio, "API_URL", apiUrl);
        ReflectionTestUtils.setField(apiExternaCambio, "chaveAcesso", chaveAcesso);
        ReflectionTestUtils.setField(apiExternaCambio, "simbolos", simbolos);
    }

    @Test
    void chamarAPIExternaCambiosTest() throws IOException {
   /*     String jsonResponse = "{\"success\":true,\"timestamp\":1678886400,\"base\":\"EUR\",\"date\":\"2023-03-15\",\"rates\":{\"USD\":1.06028,\"BRL\":5.60000}}";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(jsonResponse.getBytes());

        // Mocking the behavior of url.openConnection() directly
        when(url.openConnection()).thenReturn(connection);

        // Mocking the behavior of connection
        when(connection.getInputStream()).thenReturn(inputStream);
        when(connection.getResponseCode()).thenReturn(200);
        when(connection.getRequestMethod()).thenReturn("GET");
        when(connection.getContentType()).thenReturn("application/json");

        // Mocking the behavior of bufferedReader
        when(bufferedReader.readLine()).thenReturn(jsonResponse, null);

        ApiCambioDTO result = apiExternaCambio.chamarAPIExternaCambios();

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertEquals("EUR", result.getBase());*/
    }


}
