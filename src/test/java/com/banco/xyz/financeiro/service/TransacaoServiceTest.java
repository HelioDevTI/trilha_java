package com.banco.xyz.financeiro.service;

import com.banco.xyz.financeiro.business.CalculoTransacoes;
import com.banco.xyz.financeiro.dto.LoginDTO;
import com.banco.xyz.financeiro.dto.TransacaoAtualizarDTO;
import com.banco.xyz.financeiro.dto.TransacaoDTO;
import com.banco.xyz.financeiro.enumeration.Mes;
import com.banco.xyz.financeiro.factory.*;
import com.banco.xyz.financeiro.model.Login;
import com.banco.xyz.financeiro.model.Transacao;
import com.banco.xyz.financeiro.proxy.DadosMetricasTransacaoProxy;
import com.banco.xyz.financeiro.proxy.DadosTransacaoProxy;
import com.banco.xyz.financeiro.recod.LoginRecord;
import com.banco.xyz.financeiro.recod.TransacaoRecord;
import com.banco.xyz.financeiro.repository.TransacaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class TransacaoServiceTest {


    @MockitoBean
    private TransacaoRepository transacaoRepository;

    @MockitoBean
    private CalculoTransacoes calculoTransacoes;

    @Autowired
    private TransacaoService transacaoService;

    @Test
    void getTransacaoTest(){

        Long idConta = 1L;
        Transacao transacao = TransacaoFactory.getTransacao();
        Optional<Transacao> optionalTransacao = Optional.of(transacao);

        Mockito.when(transacaoRepository.findById(idConta))
                .thenReturn(optionalTransacao);


        TransacaoRecord transacaoRecord = transacaoService.getTransacao(idConta);


        Assertions.assertEquals(transacao.getValorConvertido(),
                transacaoRecord.valorConvertido());

    }

    @Test
    void listaTransacaoTest(){

        Transacao transacao = TransacaoFactory.getTransacao();
        Transacao transacao2 = TransacaoFactory.getTransacao();
        transacao2.setId(2L);
        transacao2.setIdTipo(2L);
        transacao2.setIdConta(2L);
        transacao2.setDescricao("Compra nova");

        List<Transacao> listaTransacao = List.of(transacao, transacao2);

        Pageable pageable = PageRequest.of(0, 2);
        Page<Transacao> page = new PageImpl<>(listaTransacao, pageable, listaTransacao.size());

        Mockito.when(transacaoRepository.findAll(pageable)).thenReturn(page);
        Page<TransacaoRecord> paginaTransacao = transacaoService.listaTransacao(pageable);

        Assertions.assertEquals(2, paginaTransacao.getTotalElements());

    }

    @Test
    void consultaTransacaoTest(){

        Long idConta =1L;
        LocalDate dataInicio = LocalDate.of(2025,1,1);
        LocalDate dataFim = LocalDate.of(2025,1,2);
        String tipoTransacao = "Nova";
        String descCompra = "Compra Nova";

        List<DadosTransacaoProxy> dados = DadosTransacaoProxyFactory.getListaDadosTransacaoProxy();

        Mockito.when(transacaoRepository
                .consultaTransacao(idConta, dataInicio, dataFim, tipoTransacao, descCompra))
                .thenReturn(dados);

        List<DadosTransacaoProxy> listaDados = transacaoService
                .consultaTransacao(idConta, dataInicio, dataFim, tipoTransacao, descCompra);

        Assertions.assertEquals(dados, listaDados);

    }

    @Test
    void consultaTransacaoDataFimNulaTest(){

        Long idConta =1L;
        LocalDate dataInicio = LocalDate.of(2025,1,1);
        LocalDate dataFim = null;
        String tipoTransacao = "Nova";
        String descCompra = "Compra Nova";

        String msgExperada = "Informa a data de incio e fim";


        RuntimeException exception = Assertions
                .assertThrows(RuntimeException.class, () -> transacaoService
                        .consultaTransacao(idConta, dataInicio, dataFim, tipoTransacao, descCompra));

        Assertions.assertEquals(msgExperada, exception.getMessage());

    }

    @Test
    void consultaTransacaoDataInicioNulaTest(){

        Long idConta =1L;
        LocalDate dataInicio = null;
        LocalDate dataFim = LocalDate.of(2025,1,2);;
        String tipoTransacao = "Nova";
        String descCompra = "Compra Nova";

        String msgExperada = "Informa a data de incio e fim";


        RuntimeException exception = Assertions
                .assertThrows(RuntimeException.class, () -> transacaoService
                        .consultaTransacao(idConta, dataInicio, dataFim, tipoTransacao, descCompra));

        Assertions.assertEquals(msgExperada, exception.getMessage());

    }

    @Test
    void consultaTransacaoDuasDatasNulaTest(){

        Long idConta =1L;
        LocalDate dataInicio = null;
        LocalDate dataFim = null;;
        String tipoTransacao = "Nova";
        String descCompra = "Compra Nova";


        List<DadosTransacaoProxy> dados = DadosTransacaoProxyFactory.getListaDadosTransacaoProxy();

        Mockito.when(transacaoRepository
                        .consultaTransacao(idConta, dataInicio, dataFim, tipoTransacao, descCompra))
                .thenReturn(dados);

        List<DadosTransacaoProxy> listaDados = transacaoService
                .consultaTransacao(idConta, dataInicio, dataFim, tipoTransacao, descCompra);

        Assertions.assertEquals(dados, listaDados);

    }

    @Test
    void consultaTransacaoDataFimAntesDataInicioTest(){

        Long idConta =1L;
        LocalDate dataInicio = LocalDate.of(2025,1,3);
        LocalDate dataFim = LocalDate.of(2025,1,2);;
        String tipoTransacao = "Nova";
        String descCompra = "Compra Nova";

        String msgExperada = "A data de incio não pode ser menor que a data fim";


        RuntimeException exception = Assertions
                .assertThrows(RuntimeException.class, () -> transacaoService
                        .consultaTransacao(idConta, dataInicio, dataFim, tipoTransacao, descCompra));

        Assertions.assertEquals(msgExperada, exception.getMessage());
    }


    @Test
    void consultaMetricasTransacoesTest(){

        Long idConta =1L;
        Mes mes = Mes.JANEIRO;
        Long ano = 2025L;

        YearMonth yearMonth = YearMonth.of(ano.intValue(), mes.getMesJavaTime());
        LocalDate ultimoDiaDoMes = yearMonth.atEndOfMonth();

        LocalDateTime iniPeriodo = LocalDateTime.of(ano.intValue(),mes.getMesJavaTime(),1,0,0,0);
        LocalDateTime fimPeriodo = LocalDateTime.of(ultimoDiaDoMes, LocalTime.of( 23,59,59));


        List<DadosMetricasTransacaoProxy> listaDados = DadosMetricasTransacaoProxyFactory
                .getDadosMetricasTransacaoProxy();

        Mockito.when(transacaoRepository
                .consutaMetricasTrasacoes(idConta, iniPeriodo, fimPeriodo))
                .thenReturn(listaDados);

        List<DadosMetricasTransacaoProxy> listaDadosRetorno = transacaoService
                .consultaMetricasTransacoes(idConta, mes, ano);


        Assertions.assertEquals(listaDados, listaDadosRetorno);


    }

    @Test
    void salvarLoginTest(){

        Transacao transacao = TransacaoFactory.getTransacao();
        TransacaoDTO transacaoDTO = TransacaoDTOFactory.getTransacaoDTO();
        BigDecimal valorConvertido = new BigDecimal("9999.99");
        transacao.setValorConvertido(valorConvertido);

        Mockito.when(transacaoRepository.save(Mockito.any())).thenReturn(transacao);
        Mockito.when(calculoTransacoes.coversaoTransacao(transacaoDTO.getValor(), transacaoDTO.getIdTipo()))
                .thenReturn(valorConvertido);
        Mockito.when(calculoTransacoes.atualizaSaldo(transacaoDTO.getIdConta(), transacaoDTO.getValor()))
                .thenReturn(true);

        TransacaoRecord transacaoRecord = transacaoService.salvarTransacao(transacaoDTO);

        Assertions.assertEquals(valorConvertido, transacaoRecord.valorConvertido());
    }

    @Test
    void salvarLoginSemSaldoTest(){

        TransacaoDTO transacaoDTO = TransacaoDTOFactory.getTransacaoDTO();

        String msgExperada = "Transacao não pode ser comcluida, saldo menor";


        RuntimeException exception = Assertions
                .assertThrows(RuntimeException.class, () -> transacaoService
                        .salvarTransacao(transacaoDTO));

        Assertions.assertEquals(msgExperada, exception.getMessage());
    }

    @Test
    void atualizarLoginTest(){

        Transacao transacao = TransacaoFactory.getTransacao();

        TransacaoAtualizarDTO transacaoAtualizarDTO = new TransacaoAtualizarDTO();
        transacaoAtualizarDTO.setValor(new BigDecimal("100.00"));
        transacaoAtualizarDTO.setDescricao("Nova Compra");
        transacaoAtualizarDTO.setId(1L);
        transacaoAtualizarDTO.setIdTipo(1L);

        BigDecimal valorConvertido = new BigDecimal("9999.99");
        transacao.setValorConvertido(valorConvertido);

        Mockito.when(transacaoRepository.save(Mockito.any())).thenReturn(transacao);
        Mockito.when(calculoTransacoes.coversaoTransacao(transacaoAtualizarDTO.getValor(), transacaoAtualizarDTO.getIdTipo()))
                .thenReturn(valorConvertido);
        Mockito.when(calculoTransacoes.atualizaSaldo(transacao.getIdConta(), transacaoAtualizarDTO.getValor().subtract(transacao.getValor())))
                .thenReturn(true);
        Mockito.when(transacaoRepository.getReferenceById(transacaoAtualizarDTO.getId())).thenReturn(transacao);

        TransacaoRecord transacaoRecord = transacaoService.atualizarTransacao(transacaoAtualizarDTO);

        Assertions.assertEquals(valorConvertido, transacaoRecord.valorConvertido());
    }

    @Test
    void atualizarLoginSemSaldoTest(){

        Transacao transacao = TransacaoFactory.getTransacao();

        TransacaoAtualizarDTO transacaoAtualizarDTO = new TransacaoAtualizarDTO();
        transacaoAtualizarDTO.setValor(new BigDecimal("100.00"));
        transacaoAtualizarDTO.setDescricao("Nova Compra");
        transacaoAtualizarDTO.setId(1L);
        transacaoAtualizarDTO.setIdTipo(1L);

        String msgExperada = "Transacao não pode ser comcluida, saldo menor";

        Mockito.when(transacaoRepository.getReferenceById(transacaoAtualizarDTO.getId())).thenReturn(transacao);

        RuntimeException exception = Assertions
                .assertThrows(RuntimeException.class, () -> transacaoService
                        .atualizarTransacao(transacaoAtualizarDTO));

        Assertions.assertEquals(msgExperada, exception.getMessage());
    }

    @Test
    void excluirTransacaoNaoEncontradaTest(){

        Long idTransacao = 1L;

        Transacao transacao = TransacaoFactory.getTransacao();

        String msg = "Transacao nao encontrada";

        Mockito.when(transacaoRepository.getReferenceById(1L)).thenReturn(new Transacao());

        ResponseEntity<String> response = transacaoService.excluirTransacao(idTransacao);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals(msg, response.getBody());
    }


    @Test
    void excluirTransacaoSaldoMenorTest(){

        Long idTransacao = 1L;

        Transacao transacao = TransacaoFactory.getTransacao();

        String msg = "Transacao não pode ser comcluida, saldo menor";

        Mockito.when(transacaoRepository.getReferenceById(idTransacao)).thenReturn(transacao);

        ResponseEntity<String> response = transacaoService.excluirTransacao(idTransacao);

        Assertions.assertEquals(HttpStatus.NOT_MODIFIED, response.getStatusCode());
        Assertions.assertEquals(msg, response.getBody());
    }

    @Test
    void excluirTransacaoTest(){

        Long idTransacao = 1L;

        Transacao transacao = TransacaoFactory.getTransacao();

        String msg = "Transacao excluida com Sucesso!";

        Mockito.when(transacaoRepository.getReferenceById(idTransacao)).thenReturn(transacao);
        Mockito.when(calculoTransacoes.atualizaSaldo(transacao.getIdConta(),
                transacao.getValor().multiply(BigDecimal.valueOf(-1)))).thenReturn(true);

        ResponseEntity<String> response = transacaoService.excluirTransacao(idTransacao);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertEquals(msg, response.getBody());
    }


}
