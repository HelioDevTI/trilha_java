package com.banco.xyz.financeiro.service;

import com.banco.xyz.financeiro.dto.LoginAtualizarDTO;
import com.banco.xyz.financeiro.dto.LoginDTO;
import com.banco.xyz.financeiro.model.Login;
import com.banco.xyz.financeiro.recod.DadosAutenticacao;
import com.banco.xyz.financeiro.recod.LoginRecord;
import com.banco.xyz.financeiro.repository.LoginRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginRecord getLogin(Long idLogin){

        return loginRepository.findById(idLogin)
                .map(log -> new LoginRecord(log.getId(), log.getIdUsuario(), log.getEmail(),
                        log.getSenha(), log.getDataLogin(), log.getDataAutlizacao()))
                .orElseThrow(EntityNotFoundException::new);



    }

    public Page<LoginRecord> listaLogins(Pageable paginacao){

        return loginRepository.findAll(paginacao).map(log -> new LoginRecord(log.getId(), log.getIdUsuario(), log.getEmail(),
                log.getSenha(), log.getDataLogin(), log.getDataAutlizacao()));
    }

    public LoginRecord salvarLogin(LoginDTO loginDTO){

        Login login = new Login();
        login.setIdUsuario(loginDTO.getIdUsuario());
        login.setEmail(loginDTO.getEmail());
        login.setSenha(passwordEncoder.encode(loginDTO.getSenha()));
        login.setDataAutlizacao(LocalDateTime.now());

        Login loginSalvo = loginRepository.save(login);

        return new LoginRecord(loginSalvo.getId(), loginSalvo.getIdUsuario(), loginSalvo.getEmail(), loginSalvo.getSenha(),
                loginSalvo.getDataLogin(), loginSalvo.getDataAutlizacao());
    }

    public LoginRecord atualizarLogin(LoginAtualizarDTO loginAtualizar){

        Login login = loginRepository.getReferenceById(loginAtualizar.getIdLogin());
        login.setId(loginAtualizar.getIdLogin());
        login.setEmail(loginAtualizar.getEmail());
        login.setSenha(passwordEncoder.encode(loginAtualizar.getSenha()));
        login.setDataAutlizacao(LocalDateTime.now());

        Login loginAtualizado = loginRepository.save(login);

        return new LoginRecord(loginAtualizado.getId(), loginAtualizado.getIdUsuario(), loginAtualizado.getEmail(),
                loginAtualizado.getSenha(), loginAtualizado.getDataLogin(), loginAtualizado.getDataAutlizacao());
    }

    public ResponseEntity<String> excluirLogin(Long id){

        Login login = loginRepository.getReferenceById(id);

        if(login.getId() == null){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Login nao encontrado");
        }

        loginRepository.delete(login);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Login excluido com Sucesso!");
    }

    public Authentication fazerLogin(DadosAutenticacao dadosAutenticacao){

       var token = new UsernamePasswordAuthenticationToken(dadosAutenticacao.email(), dadosAutenticacao.senha());

       return authenticationManager.authenticate(token);

    }

}
