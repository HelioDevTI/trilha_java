package com.banco.xyz.financeiro.service;

import com.banco.xyz.financeiro.dto.TipoTransacaoAtuliDTO;
import com.banco.xyz.financeiro.dto.TipoTransacaoDTO;
import com.banco.xyz.financeiro.model.TipoTransacao;
import com.banco.xyz.financeiro.recod.TipoTransacaoRecord;
import com.banco.xyz.financeiro.repository.TipoTransacaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TipoTransacaoService {

    @Autowired
    private TipoTransacaoRepository tipoTransacaoRepository;


    public TipoTransacaoRecord getTipoTransacao(Long idTipo){

        return tipoTransacaoRepository.findById(idTipo).map(tipo -> new TipoTransacaoRecord(tipo.getMoeda(),
                        tipo.getDescricao(), tipo.getAtivo(), tipo.getTipo(), tipo.getDataCriacao()))
                .orElseGet(null);
    }

    public Page<TipoTransacaoRecord> listaTipoTransacao(Pageable paginacao){

        return tipoTransacaoRepository.findAll(paginacao).map(tipo -> new TipoTransacaoRecord(tipo.getMoeda(),
                tipo.getDescricao(), tipo.getAtivo(), tipo.getTipo(), tipo.getDataCriacao()));
    }


    public String salvarTipoTransacao(TipoTransacaoDTO tipoDTO){

        TipoTransacao tipo =  new ModelMapper().map(tipoDTO, TipoTransacao.class);
        tipo.setId(null);
        tipo.setDataCriacao(LocalDateTime.now());

        tipoTransacaoRepository.save(tipo);

        return "Salvo com Sucesso!";
    }

    public String atualizarTipoTransacao(TipoTransacaoAtuliDTO tipoDTO){

        TipoTransacao tipo =  new ModelMapper().map(tipoDTO, TipoTransacao.class);
        tipo.setDataCriacao(LocalDateTime.now());

        if(tipoTransacaoRepository.existsById(tipo.getId())){
            tipoTransacaoRepository.save(tipo);
            return "Atualizado com Sucesso!";
        }

        return "Nao encontrado!";

    }

    public ResponseEntity<String> excluirTipoTransacao(Long id){

        TipoTransacao tipo = tipoTransacaoRepository.getReferenceById(id);

        if(tipo.getId() == null){

            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transacao nao encontrada");
        }

        tipoTransacaoRepository.delete(tipo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Trasacao excluido com Sucesso!");

    }



}
