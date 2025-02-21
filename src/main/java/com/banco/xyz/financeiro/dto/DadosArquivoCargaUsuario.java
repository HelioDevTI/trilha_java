package com.banco.xyz.financeiro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DadosArquivoCargaUsuario {

    private String nome;

    private String cpf;

    private String perfil;

    private String email;

    private String senha;

    private Long numeroConta;

    private Long digitoConta;

    private Long agenciaConta;

    private BigDecimal valorDeposito;

    private boolean isCorrentista;

}
