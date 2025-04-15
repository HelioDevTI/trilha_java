package com.banco.xyz.financeiro.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResumoDadosArquivoTransacaoDTO {

    List<DadosArquivoCargaTransacao> dadosComErro;
    List<DadosArquivoCargaTransacao> dadaosCadastrado;
}
