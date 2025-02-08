package com.banco.xyz.financeiro.service;

import com.banco.xyz.financeiro.dto.LoginAtualizarDTO;
import com.banco.xyz.financeiro.dto.LoginDTO;
import com.banco.xyz.financeiro.model.Login;
import com.banco.xyz.financeiro.recod.LoginRecord;
import com.banco.xyz.financeiro.repository.LoginRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

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

    public String salvarLogin(LoginDTO loginDTO){

        Login login = new Login();
        login.setIdUsuario(loginDTO.getIdUsuario());
        login.setEmail(loginDTO.getEmail());
        login.setSenha(loginDTO.getSenha());
        login.setDataAutlizacao(LocalDateTime.now());

        loginRepository.save(login);
        return "Salvo com Sucesso!";
    }

    public String atualizarLogin(LoginAtualizarDTO loginAtualizar){

        Login login = loginRepository.getReferenceById(loginAtualizar.getIdLogin());
        login.setId(loginAtualizar.getIdLogin());
        login.setEmail(loginAtualizar.getEmail());
        login.setSenha(loginAtualizar.getSenha());
        login.setDataAutlizacao(LocalDateTime.now());

        loginRepository.save(login);
        return "Atualizado com Sucesso!";
    }

    public ResponseEntity<String> excluirLogin(Long id){

        Login login = loginRepository.getReferenceById(id);

        if(login.getId() == null){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao encontrado");
        }

        loginRepository.delete(login);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuario excluido com Sucesso!");
    }

}
