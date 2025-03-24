package com.banco.xyz.financeiro.service;

import com.banco.xyz.financeiro.dto.ContaAtualizarDTO;
import com.banco.xyz.financeiro.dto.ContaDTO;
import com.banco.xyz.financeiro.model.Conta;
import com.banco.xyz.financeiro.recod.ContaRecord;
import com.banco.xyz.financeiro.repository.ContaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class ContaServiceTest {

    @InjectMocks
    private ContaService contaService;

    @Mock
    private ContaRepository contaRepository;

    private Conta conta;

    private List<Conta> listaConta;

    private ContaDTO contaDTO;

    private ContaAtualizarDTO contaAtualizar;


    @BeforeEach
    void setUp(){

        conta = new Conta(1L, 1L, 1L, 1L, 1L, new BigDecimal("11.11"), LocalDateTime.now());
        Conta conta2 = new Conta(2L, 2L, 2L, 2L, 2L, new BigDecimal("22.22"), LocalDateTime.now());

        listaConta = List.of(conta, conta2);

        contaDTO = new ContaDTO();
        contaDTO.setIdUsuario(1L);
        contaDTO.setNumero(123456L);
        contaDTO.setDigito(1L);
        contaDTO.setAgencia(1L);
        contaDTO.setSaldo(new BigDecimal("1000.22"));

        contaAtualizar = new ContaAtualizarDTO();
        contaAtualizar.setAgencia(1L);
        contaAtualizar.setSaldo(new BigDecimal("2000.22"));
        contaAtualizar.setNumero(1L);
        contaAtualizar.setId(1L);
        contaAtualizar.setDigito(1L);
        contaAtualizar.setIdUsuario(1L);
    }

    @Test
    void getContaTest(){

        Optional<Conta> contaOptional = Optional.of(conta);
        Mockito.when(contaRepository.findById(1L)).thenReturn(contaOptional);

        ContaRecord contaRecord = contaService.getConta(1L);

        Assertions.assertEquals(contaRecord.id(), conta.getId());
    }

    @Test
    void getContaExeptionTest(){

        Mockito.when(contaRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> contaService.getConta(1L));
    }

    @Test
    void listaContaTest(){

        Page<Conta> contaPagina = new PageImpl<>(listaConta);
        PageRequest pageRequest = PageRequest.of(0, 10);

        Mockito.when(contaRepository.findAll(pageRequest)).thenReturn(contaPagina);

        Page<ContaRecord> contas = contaService.listaConta(pageRequest);

        Assertions.assertEquals(contaPagina.stream().findFirst().get().getId(), contas.stream().findFirst().get().id());
    }

    @Test
    void salvarContaTest(){

        String msgEsperada = "Salvo com Sucesso!";

        String msgRetorno = contaService.salvarConta(contaDTO);

        Assertions.assertEquals(msgEsperada, msgRetorno);

    }

    @Test
    void atualizarContaTest(){

        String msgEsperada = "Atualizado com Sucesso!";

        Mockito.when(contaRepository.getReferenceById(contaAtualizar.getId())).thenReturn(conta);

        String msgRetorno = contaService.atualizarConta(contaAtualizar);

        Assertions.assertEquals(msgEsperada, msgRetorno);

    }

    @Test
    void excluirConta(){

        String msgEsperada = "Conta excluida com Sucesso!";

        Mockito.when(contaRepository.getReferenceById(1L)).thenReturn(conta);

        ResponseEntity<String> responseEntity = contaService.excluirConta(1L);

        Assertions.assertEquals(msgEsperada, responseEntity.getBody());

    }
}
