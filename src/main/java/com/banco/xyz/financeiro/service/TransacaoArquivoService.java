package com.banco.xyz.financeiro.service;

import com.banco.xyz.financeiro.proxy.DadosRelatorioTransacaoProxy;
import com.banco.xyz.financeiro.repository.ContaRepository;
import com.banco.xyz.financeiro.repository.TransacaoRepository;
import com.banco.xyz.financeiro.util.FinanceiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class TransacaoArquivoService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    private final static int COLUNA_A = 0;
    private final static int COLUNA_B = 1;
    private final static int COLUNA_C = 2;
    private final static int COLUNA_D = 3;
    private final static int COLUNA_E = 4;
    private final static int COLUNA_F = 5;
    private final static int LINHA_1 = 0;
    private final static int LINHA_2 = 1;
    private final static int LINHA_3 = 2;
    private final static int LINHA_5 = 4;

    private final static String FONTE = "Aptos Narrow";



    public byte[] gerarArquivoTransacao(Long idUsuairo){

        var contaUsuario = contaRepository.consultaContaUsaurio(idUsuairo);


        try (Workbook workbook = new XSSFWorkbook()){

            Sheet abaExecel = workbook.createSheet("Transacaoes");

            abaExecel.setColumnWidth(COLUNA_A, 5000);
            abaExecel.setColumnWidth(COLUNA_B, 10000);
            abaExecel.setColumnWidth(COLUNA_C, 4000);
            abaExecel.setColumnWidth(COLUNA_D, 10000);
            abaExecel.setColumnWidth(COLUNA_E, 4000);
            abaExecel.setColumnWidth(COLUNA_F, 4000);



            Row linha1 = abaExecel.createRow(LINHA_1);
            Row linha2 = abaExecel.createRow(LINHA_2);
            Row linha3 = abaExecel.createRow(LINHA_3);
            Row linha5 = abaExecel.createRow(LINHA_5);

            celulaEstiloCabecalho(workbook, linha1, COLUNA_A, "Nome");
            celulaEstiloCabecalho(workbook, linha1, COLUNA_C, "Numero");
            celulaEstiloCabecalho(workbook, linha2, COLUNA_A, "CPF");
            celulaEstiloCabecalho(workbook, linha2, COLUNA_C, "Agencia");
            celulaEstiloCabecalho(workbook, linha3, COLUNA_A, "Email");
            celulaEstiloCabecalho(workbook, linha3, COLUNA_C, "Saldo");


            celulaEstiloConteudo(workbook, linha1, COLUNA_B, contaUsuario.getNome());
            celulaEstiloConteudo(workbook, linha1, COLUNA_D, contaUsuario.getNumero());
            celulaEstiloConteudo(workbook, linha2, COLUNA_B, contaUsuario.getCPF());
            celulaEstiloConteudo(workbook, linha2, COLUNA_D, contaUsuario.getAgencia());
            celulaEstiloConteudo(workbook, linha3, COLUNA_B, contaUsuario.getEmail());
            celulaEstiloConteudo(workbook, linha3, COLUNA_D, FinanceiroUtil.formatarValor(contaUsuario.getSaldo()));


            List<String> cabecalhoTransacao = List.of("Data", "Tipo Transacao", "Tipo Cobrança", "Descrição",
                    "Valor Compra", "Valor em Reais");

            int colCabeTrasacao = 0;

            for(String ct : cabecalhoTransacao){

                celulaEstiloCabecalho(workbook, linha5, colCabeTrasacao, ct);
                colCabeTrasacao++;
            }


            List<DadosRelatorioTransacaoProxy> dadosRelatorio = transacaoRepository
                    .consultaTransacaoConta(contaUsuario.getIdConta());

            int numProxLinha = LINHA_5;

            for(DadosRelatorioTransacaoProxy dado : dadosRelatorio){

                Row linha = abaExecel.createRow(++numProxLinha);
                celulaEstiloConteudo(workbook,linha, COLUNA_A, FinanceiroUtil.converterDataString(dado.getData()));
                celulaEstiloConteudo(workbook,linha, COLUNA_B, dado.getTipoTransacao());
                celulaEstiloConteudo(workbook,linha, COLUNA_C, dado.getTipoCobranca());
                celulaEstiloConteudo(workbook,linha, COLUNA_D, dado.getDescricao());
                celulaEstiloConteudo(workbook,linha, COLUNA_E, FinanceiroUtil.formatarValor(dado.getValorCompra()));
                celulaEstiloConteudo(workbook,linha, COLUNA_F, FinanceiroUtil.formatarValor(dado.getValorReais()));

            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            return outputStream.toByteArray();


        }catch (IOException e){

            log.error("Erro para gerar Arquivo mensagem [{}]", e.getMessage());
            throw new RuntimeException("Erro para gerar Arquivo mensagem " +  e.getMessage());
        }

    }

    private void celulaEstiloCabecalho(Workbook workbook, Row linha, int letraColuna, String conteudo){

        CellStyle estiloCelula = workbook.createCellStyle();
        Font fonte = workbook.createFont();
        fonte.setFontName(FONTE);
        fonte.setBold(true);
        fonte.setColor(IndexedColors.WHITE.getIndex());
        estiloCelula.setFont(fonte);
        estiloCelula.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        estiloCelula.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloCelula.setBorderTop(BorderStyle.THIN);
        estiloCelula.setBorderBottom(BorderStyle.THIN);
        estiloCelula.setBorderLeft(BorderStyle.THIN);
        estiloCelula.setBorderRight(BorderStyle.THIN);
        estiloCelula.setAlignment(HorizontalAlignment.CENTER);
        estiloCelula.setVerticalAlignment(VerticalAlignment.CENTER);

        Cell coluna  = linha.createCell(letraColuna);
        coluna.setCellValue(conteudo);
        coluna.setCellStyle(estiloCelula);

    }

    private void celulaEstiloConteudo(Workbook workbook, Row linha, int letraColuna, Object conteudo){

        CellStyle estiloCelula = workbook.createCellStyle();
        Font fonte = workbook.createFont();
        fonte.setFontName(FONTE);
        estiloCelula.setFont(fonte);

        estiloCelula.setBorderTop(BorderStyle.THIN);
        estiloCelula.setBorderBottom(BorderStyle.THIN);
        estiloCelula.setBorderLeft(BorderStyle.THIN);
        estiloCelula.setBorderRight(BorderStyle.THIN);
        estiloCelula.setAlignment(HorizontalAlignment.LEFT);
        estiloCelula.setVerticalAlignment(VerticalAlignment.TOP);

        Cell coluna  = linha.createCell(letraColuna);

        if(conteudo instanceof String){
            coluna.setCellValue((String) conteudo);
        }else if(conteudo instanceof Long) {
            coluna.setCellValue((Long) conteudo);
        }

        coluna.setCellStyle(estiloCelula);
    }
}
