package com.banco.xyz.financeiro.service;

import com.banco.xyz.financeiro.dto.ContaAtualizarDTO;
import com.banco.xyz.financeiro.dto.ContaDTO;
import com.banco.xyz.financeiro.model.Conta;
import com.banco.xyz.financeiro.recod.ContaRecord;
import com.banco.xyz.financeiro.repository.ContaRepository;
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
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;


    public ContaRecord getConta(Long idConta){

        return contaRepository.findById(idConta).map(cot -> new ContaRecord(cot.getId(), cot.getIdUsuario(),
                cot.getNumero(), cot.getDigito(), cot.getAgencia(), cot.getSaldo(),
                        cot.getDataCriacao()))
                .orElseThrow(EntityNotFoundException::new);
    }

    public Page<ContaRecord> listaConta(Pageable paginacao){

        return contaRepository.findAll(paginacao).map(cot -> new ContaRecord(cot.getId(), cot.getIdUsuario(),
                cot.getNumero(), cot.getDigito(), cot.getAgencia(), cot.getSaldo(),
                cot.getDataCriacao()));
    }


    public ContaRecord salvarConta(ContaDTO contaDTO){

        Conta conta = new ModelMapper().map(contaDTO, Conta.class);
        conta.setDataCriacao(LocalDateTime.now());
        conta.setId(null);
       Conta contaSalva = contaRepository.save(conta);

        return new ContaRecord(contaSalva.getId(), contaSalva.getIdUsuario(), contaSalva.getNumero(),
                contaSalva.getDigito(), contaSalva.getAgencia(), contaSalva.getSaldo(), contaSalva.getDataCriacao());
    }

    public ContaRecord atualizarConta(ContaAtualizarDTO contaAtualizar){

        Conta conta = contaRepository.getReferenceById(contaAtualizar.getId());
        conta.setAgencia(contaAtualizar.getAgencia());
        conta.setNumero(contaAtualizar.getNumero());
        conta.setSaldo(contaAtualizar.getSaldo());
        conta.setDigito(contaAtualizar.getDigito());
        Conta contaAtualizada = contaRepository.save(conta);

        return  new ContaRecord(contaAtualizada.getId(), contaAtualizada.getIdUsuario(), contaAtualizada.getNumero(),
                contaAtualizada.getDigito(), contaAtualizada.getAgencia(), contaAtualizada.getSaldo(),
                contaAtualizada.getDataCriacao());
    }

    public ResponseEntity<String> excluirConta(Long id){

        Conta conta = contaRepository.getReferenceById(id);

        if(conta.getId() == null){

            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta nao encontrada");
        }

        contaRepository.delete(conta);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Conta excluida com Sucesso!");

    }

}
