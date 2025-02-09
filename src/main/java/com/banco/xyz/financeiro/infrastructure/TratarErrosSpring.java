package com.banco.xyz.financeiro.infrastructure;

import com.banco.xyz.financeiro.Exception.TokenInvalidoException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratarErrosSpring {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> erroNaotem(){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErroValidacao>> erroValidacoes(MethodArgumentNotValidException ex){

        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest()
                .body(erros.stream().map(DadosErroValidacao::new).toList());

    }

    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> erroPermissao(){

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
    }

    @ExceptionHandler(TokenInvalidoException.class)
    public ResponseEntity<String> erroTokenInvalido(TokenInvalidoException ex){

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

}
