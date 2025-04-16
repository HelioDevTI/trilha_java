package com.banco.xyz.financeiro.business;

import com.banco.xyz.financeiro.api.cambio.ApiMockCambio;
import com.banco.xyz.financeiro.api.dto.ApiCambioDTO;
import com.banco.xyz.financeiro.enumeration.SiglasMoedas;
import com.banco.xyz.financeiro.factory.ApiCambioDTOFactory;
import com.banco.xyz.financeiro.factory.TipoTransacaoFactory;
import com.banco.xyz.financeiro.model.TipoTransacao;
import com.banco.xyz.financeiro.repository.ContaRepository;
import com.banco.xyz.financeiro.repository.TipoTransacaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
@ActiveProfiles("test")
public class CalculoTransacoesMockTest {

    @MockitoBean
    private ContaRepository contaRepository;

    @MockitoBean
    private ApiMockCambio apiMockCambio;

    @Autowired
    private CalculoTransacoesMock calculoTransacoesMock;

    @MockitoBean
    private TipoTransacaoRepository tipoTransacaoRepository;

    private TipoTransacao tipoTransacao;

    private ApiCambioDTO apiCambioDTO;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException, KeyManagementException {

        tipoTransacao = TipoTransacaoFactory.getTipoTransacao();
        apiCambioDTO = ApiCambioDTOFactory.getApiCambioDTO();

        Mockito.when(apiMockCambio.chamarAPIMockCambios()).thenReturn(apiCambioDTO);
    }

    @Test
    void coversaoTransacaoMockDolarTest() throws NoSuchAlgorithmException, KeyManagementException {

         tipoTransacao.setMoeda(SiglasMoedas.DOLAR.getMoeda());

        Mockito.when(tipoTransacaoRepository.getReferenceById(1L)).thenReturn(tipoTransacao);

        BigDecimal valor = calculoTransacoesMock.coversaoTransacaoMock(new BigDecimal("50.00"), 1L);

        Assertions.assertEquals(100.00, valor.doubleValue());
    }


    @Test
    void coversaoTransacaoMockRealTest() throws NoSuchAlgorithmException, KeyManagementException {

        Mockito.when(tipoTransacaoRepository.getReferenceById(1L)).thenReturn(tipoTransacao);

        BigDecimal valor = calculoTransacoesMock.coversaoTransacaoMock(new BigDecimal("50.00"), 1L);

        Assertions.assertEquals(50.00, valor.doubleValue());
    }

    @Test
    void coversaoTransacaoMockEuroTest() throws NoSuchAlgorithmException, KeyManagementException {

        tipoTransacao.setMoeda(SiglasMoedas.EURO.getMoeda());

        Mockito.when(tipoTransacaoRepository.getReferenceById(1L)).thenReturn(tipoTransacao);

        BigDecimal valor = calculoTransacoesMock.coversaoTransacaoMock(new BigDecimal("50.00"), 1L);

        Assertions.assertEquals(4.00, valor.doubleValue());
    }


    @Test
    void coversaoTransacaoMockIeneTest() throws NoSuchAlgorithmException, KeyManagementException {

        tipoTransacao.setMoeda(SiglasMoedas.IENE.getMoeda());

        Mockito.when(tipoTransacaoRepository.getReferenceById(1L)).thenReturn(tipoTransacao);

        BigDecimal valor = calculoTransacoesMock.coversaoTransacaoMock(new BigDecimal("50.00"), 1L);

        Assertions.assertEquals(66.67, valor.doubleValue());
    }

    @Test
    void coversaoTransacaoMockPesoTest() throws NoSuchAlgorithmException, KeyManagementException {

        tipoTransacao.setMoeda(SiglasMoedas.PESO_ARGENTINO.getMoeda());

        Mockito.when(tipoTransacaoRepository.getReferenceById(1L)).thenReturn(tipoTransacao);

        BigDecimal valor = calculoTransacoesMock.coversaoTransacaoMock(new BigDecimal("50.00"), 1L);

        Assertions.assertEquals(40.00, valor.doubleValue());
    }

    @Test
    void coversaoTransacaoMockMoedaInvalidaTest(){

        tipoTransacao.setMoeda("RR$$");

        String msgExperada = "Moeda não encontrada: " + tipoTransacao.getMoeda();

        tipoTransacao.setMoeda("RR$$");

        Mockito.when(tipoTransacaoRepository.getReferenceById(1L)).thenReturn(tipoTransacao);

        IllegalStateException exception =  Assertions.assertThrows(IllegalStateException.class, () ->
                calculoTransacoesMock.coversaoTransacaoMock(new BigDecimal("50.00"), 1L));

        Assertions.assertEquals(msgExperada, exception.getMessage());
    }


}
