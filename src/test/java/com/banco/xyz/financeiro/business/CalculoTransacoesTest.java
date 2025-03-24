package com.banco.xyz.financeiro.business;

import com.banco.xyz.financeiro.api.cambio.ApiExternaCambio;
import com.banco.xyz.financeiro.api.dto.ApiCambioDTO;
import com.banco.xyz.financeiro.enumeration.SiglasMoedas;
import com.banco.xyz.financeiro.factory.ApiCambioDTOFactory;
import com.banco.xyz.financeiro.factory.ContaFactory;
import com.banco.xyz.financeiro.factory.TipoTransacaoFactory;
import com.banco.xyz.financeiro.model.Conta;
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

@SpringBootTest
@ActiveProfiles("test")
public class CalculoTransacoesTest {

    @MockitoBean
    private ContaRepository contaRepository;

    @MockitoBean
    private ApiExternaCambio apiExternaCambio;

    @Autowired
    private CalculoTransacoes calculoTransacoes;

    @MockitoBean
    private TipoTransacaoRepository tipoTransacaoRepository;

    private Conta conta;

    private TipoTransacao tipoTransacao;

    private ApiCambioDTO apiCambioDTO;

    @BeforeEach
    void setUp(){

        conta = ContaFactory.getConta();
        tipoTransacao = TipoTransacaoFactory.getTipoTransacao();
        apiCambioDTO = ApiCambioDTOFactory.getApiCambioDTO();

        Mockito.when(apiExternaCambio.chamarAPIExternaCambios()).thenReturn(apiCambioDTO);
    }

    @Test
    void atualizaSaldoTest(){


        conta.setSaldo(new BigDecimal("1000.00"));
        Mockito.when(contaRepository.getReferenceById(1L)).thenReturn(conta);

        Boolean atualizado = calculoTransacoes.atualizaSaldo(1L, new BigDecimal("50.00"));

        Assertions.assertTrue(atualizado);
    }


    @Test
    void atualizaSaldoSemSaldoTest(){

        Mockito.when(contaRepository.getReferenceById(1L)).thenReturn(conta);

        Boolean atualizado = calculoTransacoes.atualizaSaldo(1L, new BigDecimal("50.00"));

        Assertions.assertFalse(atualizado);
    }

    @Test
    void atualizaSaldoSemContaTest(){

        Mockito.when(contaRepository.getReferenceById(1L)).thenReturn(new Conta());

        Boolean atualizado = calculoTransacoes.atualizaSaldo(1L, new BigDecimal("50.00"));

        Assertions.assertFalse(atualizado);
    }

    @Test
    void coversaoTransacaoRealTest(){

        BigDecimal valorConverter = new BigDecimal("50.00");

        Mockito.when(tipoTransacaoRepository.getReferenceById(1L)).thenReturn(tipoTransacao);

        BigDecimal valor = calculoTransacoes.coversaoTransacao(valorConverter, 1L);

        Assertions.assertEquals(new BigDecimal("50.00"), valor);
    }

    @Test
    void coversaoTransacaoDolarTest(){

        BigDecimal valorConverter = new BigDecimal("50.00");
        tipoTransacao.setMoeda(SiglasMoedas.DOLAR.getMoeda());

        Mockito.when(tipoTransacaoRepository.getReferenceById(1L)).thenReturn(tipoTransacao);

        BigDecimal valor = calculoTransacoes.coversaoTransacao(valorConverter, 1L);

        Assertions.assertEquals(new BigDecimal("100.00"), valor);
    }

    @Test
    void coversaoTransacaoEuroTest(){

        BigDecimal valorConverter = new BigDecimal("50.00");
        tipoTransacao.setMoeda(SiglasMoedas.EURO.getMoeda());

        Mockito.when(tipoTransacaoRepository.getReferenceById(1L)).thenReturn(tipoTransacao);

        BigDecimal valor = calculoTransacoes.coversaoTransacao(valorConverter, 1L);

        Assertions.assertEquals(new BigDecimal("4.00"), valor);
    }

    @Test
    void coversaoTransacaoIeneTest(){

        BigDecimal valorConverter = new BigDecimal("50.00");
        tipoTransacao.setMoeda(SiglasMoedas.IENE.getMoeda());

        Mockito.when(tipoTransacaoRepository.getReferenceById(1L)).thenReturn(tipoTransacao);

        BigDecimal valor = calculoTransacoes.coversaoTransacao(valorConverter, 1L);

        Assertions.assertEquals(new BigDecimal("66.67"), valor);
    }

    @Test
    void coversaoTransacaoPesoTest(){

        BigDecimal valorConverter = new BigDecimal("50.00");
        tipoTransacao.setMoeda(SiglasMoedas.PESO_ARGENTINO.getMoeda());

        Mockito.when(tipoTransacaoRepository.getReferenceById(1L)).thenReturn(tipoTransacao);

        BigDecimal valor = calculoTransacoes.coversaoTransacao(valorConverter, 1L);

        Assertions.assertEquals(new BigDecimal("40.00"), valor);
    }

    @Test
    void coversaoTransacaoInvalidaTest(){

        BigDecimal valorConverter = new BigDecimal("50.00");
        tipoTransacao.setMoeda("Mangos");

        Mockito.when(tipoTransacaoRepository.getReferenceById(1L)).thenReturn(tipoTransacao);

        Assertions.assertThrows(IllegalStateException.class, () ->
                calculoTransacoes.coversaoTransacao(valorConverter, 1L));
    }


}
