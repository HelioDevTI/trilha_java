package com.banco.xyz.financeiro.service;

import com.banco.xyz.financeiro.dto.TipoTransacaoAtuliDTO;
import com.banco.xyz.financeiro.dto.TipoTransacaoDTO;
import com.banco.xyz.financeiro.enumeration.Tipo;
import com.banco.xyz.financeiro.factory.TipoTransacaoFactory;
import com.banco.xyz.financeiro.model.TipoTransacao;
import com.banco.xyz.financeiro.recod.TipoTransacaoRecord;
import com.banco.xyz.financeiro.repository.TipoTransacaoRepository;
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

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class TipoTransacaoServiceTest {

    @MockitoBean
    private TipoTransacaoRepository tipoTransacaoRepository;

    @Autowired
    private TipoTransacaoService transacaoService;


    @Test
    void getTipoTransacaoTest(){

        Long idTipo = 1L;

        TipoTransacao transacao = TipoTransacaoFactory.getTipoTransacao();
        Optional<TipoTransacao> tipoTransacaoOptional = Optional.of(transacao);

        Mockito.when(tipoTransacaoRepository.findById(idTipo)).thenReturn(tipoTransacaoOptional);

        TipoTransacaoRecord transacaoRecord = transacaoService.getTipoTransacao(idTipo);

        Assertions.assertEquals(transacao.getId(), transacaoRecord.id());
    }

    @Test
    void listaTipoTransacaoTest(){

        TipoTransacao transacao = TipoTransacaoFactory.getTipoTransacao();
        TipoTransacao transacao2 = TipoTransacaoFactory.getTipoTransacao();
        transacao2.setId(2L);
        transacao2.setTipo(Tipo.CREDITO);
        transacao2.setDescricao("Nova Transacao");

        List<TipoTransacao> listaTipo = List.of(transacao, transacao2);

        Pageable pageable = PageRequest.of(0, 2);
        Page<TipoTransacao> page = new PageImpl<>(listaTipo, pageable, listaTipo.size());

        Mockito.when(tipoTransacaoRepository.findAll(pageable)).thenReturn(page);

        Page<TipoTransacaoRecord> paginaLogin = transacaoService.listaTipoTransacao(pageable);

        Assertions.assertEquals(2, paginaLogin.getTotalElements());
    }

    @Test
    void salvarTipoTransacaoTest(){

        TipoTransacao transacao = TipoTransacaoFactory.getTipoTransacao();
        TipoTransacaoDTO tipoTransacaoDTO = new TipoTransacaoDTO();
        tipoTransacaoDTO.setAtivo(transacao.getAtivo());
        tipoTransacaoDTO.setMoeda(transacao.getMoeda());
        tipoTransacaoDTO.setTipo(Tipo.DEBITO);
        tipoTransacaoDTO.setDescricao(transacao.getDescricao());


        Mockito.when(tipoTransacaoRepository.save(Mockito.any())).thenReturn(transacao);

        TipoTransacaoRecord tipoTransacaoRecord = transacaoService.salvarTipoTransacao(tipoTransacaoDTO);

        Assertions.assertEquals(transacao.getDescricao(), tipoTransacaoRecord.descricao());
    }

    @Test
    void atualizarTipoTransacaoTest(){

        TipoTransacao transacao = TipoTransacaoFactory.getTipoTransacao();
        TipoTransacaoAtuliDTO tipoTransacaoDTO = new TipoTransacaoAtuliDTO();
        tipoTransacaoDTO.setAtivo(transacao.getAtivo());
        tipoTransacaoDTO.setMoeda(transacao.getMoeda());
        tipoTransacaoDTO.setTipo(Tipo.DEBITO);
        tipoTransacaoDTO.setDescricao(transacao.getDescricao());
        tipoTransacaoDTO.setId(transacao.getId());


        Mockito.when(tipoTransacaoRepository.save(Mockito.any())).thenReturn(transacao);
        Mockito.when(tipoTransacaoRepository.existsById(transacao.getId())).thenReturn(true);

        TipoTransacaoRecord tipoTransacaoRecord = transacaoService.atualizarTipoTransacao(tipoTransacaoDTO);

        Assertions.assertEquals(transacao.getDescricao(), tipoTransacaoRecord.descricao());
    }

    @Test
    void excluirLoginNaoEncontradoTest(){

        String msg = "Tipo Transacao nao encontrada";

        Mockito.when(tipoTransacaoRepository.getReferenceById(1L)).thenReturn(new TipoTransacao());

        ResponseEntity<String> response = transacaoService.excluirTipoTransacao(1L);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals(msg, response.getBody());
    }

    @Test
    void excluirLoginTest(){

        String msg = "Tipo Trasacao excluido com Sucesso!";

        Mockito.when(tipoTransacaoRepository.getReferenceById(1L)).thenReturn(TipoTransacaoFactory.getTipoTransacao());

        ResponseEntity<String> response = transacaoService.excluirTipoTransacao(1L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertEquals(msg, response.getBody());
    }


}
