package com.banco.xyz.financeiro.service;

import com.banco.xyz.financeiro.business.CalculoTransacoes;
import com.banco.xyz.financeiro.dto.TransacaoAtualizarDTO;
import com.banco.xyz.financeiro.dto.TransacaoDTO;
import com.banco.xyz.financeiro.enumeration.Mes;
import com.banco.xyz.financeiro.model.Transacao;
import com.banco.xyz.financeiro.proxy.DadosMetricasTransacaoProxy;
import com.banco.xyz.financeiro.proxy.DadosTransacaoProxy;
import com.banco.xyz.financeiro.recod.TransacaoRecord;
import com.banco.xyz.financeiro.repository.TransacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;

@Slf4j
@Service
public class TransacaoService {


    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private CalculoTransacoes calculoTransacoes;


    public TransacaoRecord getTransacao(Long idConta){

        return transacaoRepository.findById(idConta).map(tra -> new TransacaoRecord(tra.getId(), tra.getIdTipo(), tra.getIdConta(),
                        tra.getDescricao(), tra.getValor(), tra.getValorConvertido(), tra.getDataTransacao()))
                .orElseThrow(EntityNotFoundException::new);
    }

    public Page<TransacaoRecord> listaTransacao(Pageable paginacao){

        return transacaoRepository.findAll(paginacao).map(tra -> new TransacaoRecord(tra.getId(), tra.getIdTipo(), tra.getIdConta(),
                tra.getDescricao(), tra.getValor(), tra.getValorConvertido(),  tra.getDataTransacao()));
    }

    public List<DadosTransacaoProxy> consultaTransacao(Long idUsuario, LocalDate dataInicio, LocalDate dataFim, String tipoTransacao,
                                                       String descCompra){

        if((dataInicio == null && dataFim != null) || (dataInicio != null && dataFim == null)){

            throw new RuntimeException("Informa a data de incio e fim");

        }

        if(dataInicio != null && dataFim.isBefore(dataInicio)){

            throw new RuntimeException("A data de incio não pode ser menor que a data fim");

        }

     return transacaoRepository.consultaTransacao(idUsuario, dataInicio, dataFim, tipoTransacao, descCompra);

    }

    public List<DadosMetricasTransacaoProxy> consultaMetricasTransacoes(Long idConta, Mes mes, Long ano){

        YearMonth yearMonth = YearMonth.of(ano.intValue(), mes.getMesJavaTime());
        LocalDate ultimoDiaDoMes = yearMonth.atEndOfMonth();

        LocalDateTime iniPeriodo = LocalDateTime.of(ano.intValue(),mes.getMesJavaTime(),1,0,0,0);
        LocalDateTime fimPeriodo = LocalDateTime.of(ultimoDiaDoMes, LocalTime.of( 23,59,59));

        return transacaoRepository.consutaMetricasTrasacoes(idConta, iniPeriodo, fimPeriodo);
    }

    public TransacaoRecord salvarTransacao(TransacaoDTO transacaoDTO){


        if(!calculoTransacoes.atualizaSaldo(transacaoDTO.getIdConta(), transacaoDTO.getValor())){
            throw new RuntimeException("Transacao não pode ser comcluida, saldo menor");
        }

        Transacao transacao = new Transacao();
        transacao.setIdConta(transacaoDTO.getIdConta());
        transacao.setIdTipo(transacaoDTO.getIdTipo());
        transacao.setDescricao(transacaoDTO.getDescricao());
        transacao.setValor(transacaoDTO.getValor());
        transacao.setDataTransacao(LocalDateTime.now());
        transacao.setValorConvertido(calculoTransacoes.coversaoTransacao(transacaoDTO.getValor(), transacaoDTO.getIdTipo()));
        Transacao transacaoSalva =transacaoRepository.save(transacao);

        return new TransacaoRecord(transacaoSalva.getId(), transacaoSalva.getIdTipo(), transacaoSalva.getIdConta(),
                transacaoSalva.getDescricao(), transacaoSalva.getValor(), transacaoSalva.getValorConvertido(), transacaoSalva.getDataTransacao());
    }

    public TransacaoRecord atualizarTransacao(TransacaoAtualizarDTO transacaoDTO){

        Transacao transacao = transacaoRepository.getReferenceById(transacaoDTO.getId());


        if(!calculoTransacoes.atualizaSaldo(transacao.getIdConta(), transacaoDTO.getValor().subtract(transacao.getValor()))){
            throw new RuntimeException("Transacao não pode ser comcluida, saldo menor");
        }

        transacao.setIdTipo(transacaoDTO.getIdTipo());
        transacao.setDescricao(transacaoDTO.getDescricao());
        transacao.setValor(transacaoDTO.getValor());
        transacao.setValorConvertido(calculoTransacoes.coversaoTransacao(transacaoDTO.getValor(), transacaoDTO.getIdTipo()));
        Transacao transacaoAuatilizada = transacaoRepository.save(transacao);

        return  new TransacaoRecord(transacaoAuatilizada.getId(), transacaoAuatilizada.getIdTipo(), transacaoAuatilizada.getIdConta(),
                transacaoAuatilizada.getDescricao(), transacaoAuatilizada.getValor(),
                transacaoAuatilizada.getValorConvertido(), transacaoAuatilizada.getDataTransacao());
    }

    public ResponseEntity<String> excluirTransacao(Long id){

        Transacao transacao = transacaoRepository.getReferenceById(id);

        if(transacao.getId() == null){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transacao nao encontrada");
        }


        if(!calculoTransacoes.atualizaSaldo(transacao.getIdConta(), transacao.getValor().multiply(BigDecimal.valueOf(-1)))){
           return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Transacao não pode ser comcluida, saldo menor");
        }


        transacaoRepository.delete(transacao);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Transacao excluida com Sucesso!");

    }

}
