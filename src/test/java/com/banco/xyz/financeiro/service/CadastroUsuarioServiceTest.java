package com.banco.xyz.financeiro.service;

import com.banco.xyz.financeiro.dto.DadosCorrentistaDTO;
import com.banco.xyz.financeiro.recod.CorrentistaRecord;
import com.banco.xyz.financeiro.repository.ContaRepository;
import com.banco.xyz.financeiro.repository.LoginRepository;
import com.banco.xyz.financeiro.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

@SpringBootTest
@ActiveProfiles("test")
public class CadastroUsuarioServiceTest {

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private LoginRepository loginRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void cadastroCorrentistaTest(){

        DadosCorrentistaDTO dados=  cadastroUsuarioService
                .cadastroCorrentista(new CorrentistaRecord("Teste", "111.111.111-11",
                1L, 1L, 1L, new BigDecimal("10.10"), "teste@teste.com", "1234"));

        Assertions.assertEquals("Teste", dados.getUsuario().nome());
    }
}
