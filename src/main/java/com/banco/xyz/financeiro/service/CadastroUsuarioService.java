package com.banco.xyz.financeiro.service;

import com.banco.xyz.financeiro.dto.DadosCorrentistaDTO;
import com.banco.xyz.financeiro.model.Conta;
import com.banco.xyz.financeiro.model.Login;
import com.banco.xyz.financeiro.model.Usuario;
import com.banco.xyz.financeiro.recod.ContaRecord;
import com.banco.xyz.financeiro.recod.CorrentistaRecord;
import com.banco.xyz.financeiro.recod.LoginRecord;
import com.banco.xyz.financeiro.recod.UsuarioRecord;
import com.banco.xyz.financeiro.repository.ContaRepository;
import com.banco.xyz.financeiro.repository.LoginRepository;
import com.banco.xyz.financeiro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CadastroUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private LoginRepository loginRepository;

    public DadosCorrentistaDTO cadastroCorrentista(CorrentistaRecord correntista){


        Long perfilCorrentista = 3L;
        LocalDateTime dataAtual = LocalDateTime.now();

        Usuario usuario = new Usuario(null, perfilCorrentista, correntista.nome(),
                correntista.cpf(), dataAtual);

        Usuario usarioSalvo = usuarioRepository.save(usuario);

        Conta conta = new Conta(null, usarioSalvo.getId(),
                correntista.numero(), correntista.digito(),
                correntista.agencia(), correntista.valorDeposito(), dataAtual);

        Conta contaSalva = contaRepository.save(conta);


        Login login = new Login(null, usarioSalvo.getId(), correntista.email(),
                correntista.senha(), null, dataAtual);

        Login loginSalvo = loginRepository.save(login);


        return new DadosCorrentistaDTO(new UsuarioRecord(usarioSalvo),
              new ContaRecord(contaSalva), new LoginRecord(loginSalvo));

    }
}
