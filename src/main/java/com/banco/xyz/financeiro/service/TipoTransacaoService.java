package com.banco.xyz.financeiro.service;

import com.banco.xyz.financeiro.dto.TipoTransacaoAtuliDTO;
import com.banco.xyz.financeiro.dto.TipoTransacaoDTO;
import com.banco.xyz.financeiro.model.TipoTransacao;
import com.banco.xyz.financeiro.recod.TipoTransacaoRecord;
import com.banco.xyz.financeiro.repository.TipoTransacaoRepository;
import jakarta.persistence.EntityNotFoundException;
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

        return tipoTransacaoRepository.findById(idTipo).map(tipo -> new TipoTransacaoRecord(tipo.getId(), tipo.getMoeda(),
                        tipo.getDescricao(), tipo.getAtivo(), tipo.getTipo(), tipo.getDataCriacao()))
                .orElseThrow(EntityNotFoundException::new);
    }

    public Page<TipoTransacaoRecord> listaTipoTransacao(Pageable paginacao){

        return tipoTransacaoRepository.findAll(paginacao).map(tipo -> new TipoTransacaoRecord(tipo.getId(), tipo.getMoeda(),
                tipo.getDescricao(), tipo.getAtivo(), tipo.getTipo(), tipo.getDataCriacao()));
    }


    public TipoTransacaoRecord salvarTipoTransacao(TipoTransacaoDTO tipoDTO){

        TipoTransacao tipo =  new ModelMapper().map(tipoDTO, TipoTransacao.class);
        tipo.setId(null);
        tipo.setDataCriacao(LocalDateTime.now());

        TipoTransacao tipoSalvo = tipoTransacaoRepository.save(tipo);

        return new TipoTransacaoRecord(tipo.getId(), tipoSalvo.getMoeda(), tipoSalvo.getDescricao(),
                tipoSalvo.getAtivo(), tipoSalvo.getTipo(), tipoSalvo.getDataCriacao());
    }

    public TipoTransacaoRecord atualizarTipoTransacao(TipoTransacaoAtuliDTO tipoDTO){

        TipoTransacao tipo =  new ModelMapper().map(tipoDTO, TipoTransacao.class);
        tipo.setDataCriacao(LocalDateTime.now());

        if(tipoTransacaoRepository.existsById(tipo.getId())){

           TipoTransacao tipoAtualizado = tipoTransacaoRepository.save(tipo);
            return new TipoTransacaoRecord(tipo.getId(), tipoAtualizado.getMoeda(), tipoAtualizado.getDescricao(),
                    tipoAtualizado.getAtivo(), tipoAtualizado.getTipo(), tipoAtualizado.getDataCriacao());

        }

        return null;

    }

    public ResponseEntity<String> excluirTipoTransacao(Long id){

        TipoTransacao tipo = tipoTransacaoRepository.getReferenceById(id);

        if(tipo.getId() == null){

           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo Transacao nao encontrada");
        }

        tipoTransacaoRepository.delete(tipo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Tipo Trasacao excluido com Sucesso!");

    }



}
