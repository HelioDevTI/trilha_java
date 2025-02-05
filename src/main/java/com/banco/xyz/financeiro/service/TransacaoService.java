package com.banco.xyz.financeiro.service;

import com.banco.xyz.financeiro.dto.TransacaoAtualizarDTO;
import com.banco.xyz.financeiro.dto.TransacaoDTO;
import com.banco.xyz.financeiro.model.Transacao;
import com.banco.xyz.financeiro.recod.TransacaoRecord;
import com.banco.xyz.financeiro.repository.TransacaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransacaoService {


    @Autowired
    private TransacaoRepository transacaoRepository;


    public TransacaoRecord getTransacao(Long idConta){

        return transacaoRepository.findById(idConta).map(tra -> new TransacaoRecord(tra.getIdTipo(), tra.getIdConta(),
                        tra.getDescricao(), tra.getValor(), tra.getValorConvertido(), tra.getDataTransacao()))
                .orElseGet(null);
    }

    public Page<TransacaoRecord> listaTransacao(Pageable paginacao){

        return transacaoRepository.findAll(paginacao).map(tra -> new TransacaoRecord(tra.getIdTipo(), tra.getIdConta(),
                tra.getDescricao(), tra.getValor(), tra.getValorConvertido(),  tra.getDataTransacao()));
    }


    public String salvarTransacao(TransacaoDTO transacaoDTO){

        Transacao transacao = new Transacao();
        transacao.setIdConta(transacaoDTO.getIdConta());
        transacao.setIdTipo(transacaoDTO.getIdTipo());
        transacao.setDescricao(transacaoDTO.getDescricao());
        transacao.setValor(transacaoDTO.getValor());
        transacao.setDataTransacao(LocalDateTime.now());
        transacao.setValorConvertido(BigDecimal.ZERO);// TODO altera para API Externa
        transacaoRepository.save(transacao);

        return "Salvo com Sucesso!";
    }

    public String atualizarTransacao(TransacaoAtualizarDTO transacaoDTO){

        Transacao transacao = transacaoRepository.getReferenceById(transacaoDTO.getId());
        transacao.setIdTipo(transacaoDTO.getIdTipo());
        transacao.setDescricao(transacaoDTO.getDescricao());
        transacao.setValor(transacaoDTO.getValor());
        transacao.setValorConvertido(BigDecimal.ZERO);// TODO altera para API Externa
        transacaoRepository.save(transacao);

        return "Atualizado com Sucesso!";
    }

    public ResponseEntity<String> excluirTransacao(Long id){

        Transacao transacao = transacaoRepository.getReferenceById(id);

        if(transacao.getId() == null){

            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transacao nao encontrada");
        }

        transacaoRepository.delete(transacao);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Transacao excluida com Sucesso!");

    }

}
