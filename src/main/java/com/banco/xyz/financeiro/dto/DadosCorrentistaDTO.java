package com.banco.xyz.financeiro.dto;

import com.banco.xyz.financeiro.recod.ContaRecord;
import com.banco.xyz.financeiro.recod.LoginRecord;
import com.banco.xyz.financeiro.recod.UsuarioRecord;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DadosCorrentistaDTO {

    private UsuarioRecord usuario;

    private ContaRecord conta;

    private LoginRecord login;
}
